package br.com.dbccompany.vemser.tests.edicao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.EdicaoClient;

import java.util.*;

@DisplayName("Endpoint de castro de edição")
public class CadastrarEdicaoTest extends BaseTest {

    private static EdicaoClient edicaoClient = new EdicaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 ao cadastrar edição com sucesso")
    public void testCadastrarEdicaoComSucesso() {

        var response = edicaoClient.listarTodasAsEdicoes()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EdicaoModel[].class);

        List<EdicaoModel> listaDeEdicoes = Arrays.stream(response).toList();

        List<EdicaoModel> listaDeEdicoesOrdenada = new ArrayList<>(listaDeEdicoes);

        Collections.sort(listaDeEdicoesOrdenada, new Comparator<EdicaoModel>() {
            public int compare(EdicaoModel edicao1, EdicaoModel edicao2) {
                return Integer.compare(edicao2.idEdicao, edicao1.idEdicao);
            }
        });

        Integer idUltimaEdicao = listaDeEdicoesOrdenada.get(0).getIdEdicao();
        Integer idNovaEdicao = idUltimaEdicao + 4;

        EdicaoModel edicaoCadastrada = edicaoClient.criarEdicaoComNumEdicao(idNovaEdicao)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EdicaoModel.class);

        edicaoClient.deletarEdicao(edicaoCadastrada.getIdEdicao());
        Assertions.assertNotNull(edicaoCadastrada);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao cadastrar edição sem autenticação")
    public void testCadastrarEdicaoSemAutenticacao() {

        var response = edicaoClient.listarTodasAsEdicoes()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EdicaoModel[].class);

        List<EdicaoModel> listaDeEdicoes = Arrays.stream(response).toList();

        List<EdicaoModel> listaDeEdicoesOrdenada = new ArrayList<>(listaDeEdicoes);

        Collections.sort(listaDeEdicoesOrdenada, new Comparator<EdicaoModel>() {
            public int compare(EdicaoModel edicao1, EdicaoModel edicao2) {
                return edicao2.idEdicao.compareTo(edicao1.idEdicao);
            }
        });

        Integer idUltimaEdicao = listaDeEdicoesOrdenada.get(0).getIdEdicao();
        Integer idNovaEdicao = idUltimaEdicao + 4;

        var edicaoCadastrada = edicaoClient.criarEdicaoComNumEdicaoSemAutenticacao(idNovaEdicao)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
