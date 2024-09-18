package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Endpoint de listagem de entrevistas por trilha")
class ListarEntrevistaPorTrilhaTest {
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista entrevistas de trilhas existentes")
    @Tag("Regression")
    void testListarEntrevistasPorTrilhaComSucesso() {
        String trilha = "QA";

        var lista = entrevistaClient.listarTodasAsEntrevistasPorTrilha(trilha)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel[].class);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 quando lista entrevistas de trilhas não existentes")
    @Tag("Regression")
    void testListarEntrevistasPorTrilhaNaoExistente() {
        String trilhaNaoExistente = "-*/-*/-*/-*/-*/";

        var lista = entrevistaClient.listarTodasAsEntrevistasPorTrilha(trilhaNaoExistente)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(equalTo("[]"));
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 quando lista entrevistas de trilhas sem autenticação")
    @Tag("Regression")
    void testListarEntrevistasPorTrilhaSemAutenticacao() {
        String trilha = "QA";

        var lista = entrevistaClient.listarTodasAsEntrevistasPorTrilhaSemAutenticacao(trilha)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
