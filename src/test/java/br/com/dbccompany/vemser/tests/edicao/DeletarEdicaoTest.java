package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import factory.edicao.EdicaoDataFactory;
import io.restassured.response.Response;
import models.edicao.EdicaoModel;
import models.edicao.EdicaoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("Endpoint de remoção de edição")
class DeletarEdicaoTest {

    private static final EdicaoClient edicaoClient = new EdicaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar edição com sucesso")
    void testDeletarEdicaoComSucesso() {
        EdicaoModel edicaoCadastrada = EdicaoDataFactory.edicaoValida();

        EdicaoResponse edicaoResponse = edicaoClient.cadastrarEdicao(edicaoCadastrada)
                .then()
                .log().all()
                .statusCode(201)
                .extract().as(EdicaoResponse.class);

        Integer idEdicao = Integer.parseInt(String.valueOf(edicaoResponse.getIdEdicao()));

        Response response = edicaoClient.deletarEdicao(idEdicao);

        response.then().statusCode(204);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar edição sem autenticação")
    void testDeletarEdicaoSemAutenticacao() {
        // Cadastrar uma edição válida para obter seu ID
        EdicaoModel cadastrarEdicao = EdicaoDataFactory.edicaoValida();

        EdicaoResponse edicaoCadastrada = edicaoClient.cadastrarEdicao(cadastrarEdicao)
                .then()
                .log().all()
                .statusCode(201)
                .extract().as(EdicaoResponse.class);

        edicaoClient.deletarEdicaoSemAutenticacao(edicaoCadastrada.getIdEdicao())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
