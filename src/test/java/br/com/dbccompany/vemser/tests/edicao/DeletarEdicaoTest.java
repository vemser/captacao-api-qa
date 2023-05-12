package br.com.dbccompany.vemser.tests.edicao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.EdicaoService;

import java.util.*;

@DisplayName("Endpoint de remoção de edição")
public class DeletarEdicaoTest extends BaseTest {

    private static EdicaoService edicaoService = new EdicaoService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar edição com sucesso")
    public void testDeletarEdicaoComSucesso() {

        var response = edicaoService.listarTodasAsEdicoes()
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

        EdicaoModel edicaoCadastrada = edicaoService.criarEdicaoComNumEdicao(idNovaEdicao)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EdicaoModel.class);

        var deletarEdicao = edicaoService.deletarEdicaoComResponse(edicaoCadastrada.getIdEdicao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        var confirmaDelecaoEdicao = edicaoService.deletarEdicaoComResponse(edicaoCadastrada.getIdEdicao())
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao deletar edição sem autenticação")
    public void testDeletarEdicaoSemAutenticacao() {

        var response = edicaoService.listarTodasAsEdicoes()
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

        EdicaoModel edicaoCadastrada = edicaoService.criarEdicaoComNumEdicao(idNovaEdicao)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EdicaoModel.class);

        var deletarEdicaoSemAutenticar = edicaoService.deletarEdicaoComResponseSemAutenticacao(edicaoCadastrada.getIdEdicao())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarEdicaoComSucesso = edicaoService.deletarEdicaoComResponse(edicaoCadastrada.getIdEdicao())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
