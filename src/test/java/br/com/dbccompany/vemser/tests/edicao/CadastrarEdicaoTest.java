package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("Endpoint de castro de edição")
class CadastrarEdicaoTest {

    private static final EdicaoClient edicaoClient = new EdicaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 ao cadastrar edição com sucesso")
    void testCadastrarEdicaoComSucesso() {

        var response = edicaoClient.listarTodasAsEdicoes()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EdicaoModel[].class);

        List<EdicaoModel> listaDeEdicoes = Arrays.stream(response).toList();

        List<EdicaoModel> listaDeEdicoesOrdenada = new ArrayList<>(listaDeEdicoes);

        listaDeEdicoesOrdenada.sort((edicao1, edicao2) -> Integer.compare(edicao2.getIdEdicao(), edicao1.getIdEdicao()));


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
    void testCadastrarEdicaoSemAutenticacao() {

        var response = edicaoClient.listarTodasAsEdicoes()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EdicaoModel[].class);

        List<EdicaoModel> listaDeEdicoes = Arrays.stream(response).toList();

        List<EdicaoModel> listaDeEdicoesOrdenada = new ArrayList<>(listaDeEdicoes);

        listaDeEdicoesOrdenada.sort((edicao1, edicao2) -> edicao2.getIdEdicao().compareTo(edicao1.getIdEdicao()));

        Integer idUltimaEdicao = listaDeEdicoesOrdenada.get(0).getIdEdicao();
        Integer idNovaEdicao = idUltimaEdicao + 4;

        var edicaoCadastrada = edicaoClient.criarEdicaoComNumEdicaoSemAutenticacao(idNovaEdicao)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
