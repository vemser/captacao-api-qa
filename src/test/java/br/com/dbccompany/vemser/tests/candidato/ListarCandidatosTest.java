package br.com.dbccompany.vemser.tests.candidato;

import client.CandidatoClient;
import models.failure.JSONFailureResponseWithArrayModel;
import models.candidato.JSONListaCandidatoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de candidatos")
class ListarCandidatosTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 e lista contendo 20 elementos com sucesso")
    @Tag("Regression")
    void testListarTodosOsCandidatos() {

        Integer tamanho = 20;
        Integer pagina = 0;

        JSONListaCandidatoResponse listaCandidato =
            candidatoClient.listarTodosOsCandidatos(pagina, tamanho)
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                 .as(JSONListaCandidatoResponse.class);

        Integer idCandidato1 = listaCandidato.getElementos().get(0).getIdCandidato();
        Integer idCandidato2 = listaCandidato.getElementos().get(1).getIdCandidato();

        Assertions.assertTrue(tamanho >= listaCandidato.elementos.size());
        Assertions.assertTrue(idCandidato2 > idCandidato1);
        Assertions.assertNotNull(listaCandidato.getTotalElementos());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getNome());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getDataNascimento());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEmail());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getTelefone());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getRg());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getCpf());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEstado());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getCidade());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getPcd());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getAtivo());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getLinguagens());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEdicao().getIdEdicao());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEdicao().getNome());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEdicao().getNotaCorte());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve validar o contrato de listagem de candidatos no sistema")
    @Tag("Regression")
    void testValidarContratoListarCandidatos() {

        candidatoClient.listarTodosOsCandidatos(0, 20)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(matchesJsonSchemaInClasspath("schemas/candidato/ListarCandidato.json"));
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 200 e body deve conter 10 candidatos")
    @Tag("Regression")
    void testListarDezCandidatos() {

        Integer tamanhoPadraoLista = 10;
        Integer pagina = 1;

        JSONListaCandidatoResponse listaCandidatoResponse =
            candidatoClient.listarTodosOsCandidatos(pagina, tamanhoPadraoLista)
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                    .as(JSONListaCandidatoResponse.class);

        Assertions.assertTrue(tamanhoPadraoLista >= listaCandidatoResponse.getElementos().size());
        Assertions.assertEquals(tamanhoPadraoLista, listaCandidatoResponse.tamanho);
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 quando é passado quantidade negativa de candidatos")
    @Tag("Regression")
    void testListarCandidatosComTamanhoInvalido() {
        Integer numDeCandidatos = -5;

        JSONFailureResponseWithArrayModel failureResponse =
            candidatoClient.listarNumCandidatos(numDeCandidatos)
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, failureResponse.getStatus());
        Assertions.assertEquals("O tamanho não pode ser menor do que 1.", failureResponse.getMessage());
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 400 quando é passado quantidade de candidatos como string")
    @Tag("Regression")
    void testListarCandidatosComTamanhoString() {
        String numDeCandidatos = "abc";

        candidatoClient.listarNumCandidatos(numDeCandidatos)
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
