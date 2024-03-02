package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import io.restassured.response.Response;
import models.JSONFailureResponseWithArrayModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.candidato.CandidatoResponseModel;
import models.candidato.JSONListaCandidatoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de listagem de candidatos")
class ListarCandidatosTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 e lista contendo 20 elementos com sucesso")
    void testListarTodosOsCandidatos() {


        JSONListaCandidatoResponse listaCandidatoResponse = candidatoClient.listarTodosOsCandidatos()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(JSONListaCandidatoResponse.class);

        Integer tamanhoPadraoLista = 20;
        Integer idCandidato1 = listaCandidatoResponse.getElementos().get(0).getIdCandidato();
        Integer idCandidato2 = listaCandidatoResponse.getElementos().get(1).getIdCandidato();

        Assertions.assertEquals(tamanhoPadraoLista, listaCandidatoResponse.elementos.size());
        Assertions.assertTrue(idCandidato2 > idCandidato1);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 e body deve conter 10 candidatos")
    void testListarDezCandidatos() {
        Integer numDeCandidatos = 10;

        JSONListaCandidatoResponse listaCandidatoResponse = candidatoClient.listarNumCandidatos(numDeCandidatos)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(JSONListaCandidatoResponse.class);

        Assertions.assertEquals(numDeCandidatos, listaCandidatoResponse.getElementos().size());
        Assertions.assertEquals(numDeCandidatos, listaCandidatoResponse.tamanho);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 400 quando é passado qtd negativa de candidatos")
    void testListarCandidatosComTamanhoInvalido() {
        Integer numDeCandidatos = -5;

        JSONFailureResponseWithArrayModel failureResponse = candidatoClient.listarNumCandidatos(numDeCandidatos)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);


        Assertions.assertEquals(400, failureResponse.getStatus());
        Assertions.assertEquals("O tamanho não pode ser menor do que 1.", failureResponse.getMessage());
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 quando é passado qtd de candidatos como string")
    void testListarCandidatosComTamanhoString() {
        String numDeCandidatos = "abc";

        var failureResponse = candidatoClient.listarNumCandidatos(numDeCandidatos)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
