package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import factory.edicao.EdicaoDataFactory;
import io.restassured.response.Response;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de edição")
class DeletarEdicaoTest {

    private static final EdicaoClient edicaoClient = new EdicaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar edição com sucesso")
    @Tag("Regression")
    void testDeletarEdicaoComSucesso() {
        EdicaoModel edicaoCadastrada = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoResponse = edicaoClient.criarEdicao(edicaoCadastrada);


        Integer idEdicao = Integer.parseInt(String.valueOf(edicaoResponse.getIdEdicao()));

        Response response = edicaoClient.deletarEdicao(idEdicao);

        response.then().statusCode(204);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar edição sem autenticação")
    void testDeletarEdicaoSemAutenticacao() {

        EdicaoModel cadastrarEdicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCadastrada = edicaoClient.criarEdicao(cadastrarEdicao);

        edicaoClient.deletarEdicaoSemAutenticacao(edicaoCadastrada.getIdEdicao())
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
