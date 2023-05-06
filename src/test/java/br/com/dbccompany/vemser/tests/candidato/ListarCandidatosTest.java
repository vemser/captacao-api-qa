package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.JSONFailureResponseWithArrayModel;
import models.candidato.JSONListaCandidatoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;

@DisplayName("Endpoint de listagem de candidatos")
public class ListarCandidatosTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 e lista contendo 20 elementos com sucesso")
    public void testListarTodosOsCandidatos() {


        JSONListaCandidatoResponse listaCandidatoResponse = candidatoService.listarTodosOsCandidatos()
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
    @DisplayName("Cenário 2: Deve retornar 403 sem body por não estar autenticado")
    public void testListarTodosOsCandidatosSemAutenticacao() {

        try {
            var response = candidatoService.listarTodosOsCandidatosSemAutenticacao()
                    .then()
                        .log().status()
                        .statusCode(HttpStatus.SC_FORBIDDEN)
                        .extract()
                        .as(JSONListaCandidatoResponse.class);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 200 e body deve conter 10 candidatos")
    public void testListarDezCandidatos() {
        Integer numDeCandidatos = 10;

        JSONListaCandidatoResponse listaCandidatoResponse = candidatoService.listarNumCandidatos(numDeCandidatos)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(JSONListaCandidatoResponse.class);

        Assertions.assertEquals(numDeCandidatos, listaCandidatoResponse.getElementos().size());
        Assertions.assertEquals(numDeCandidatos, listaCandidatoResponse.tamanho);
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 quando é passado qtd negativa de candidatos")
    public void testListarCandidatosComTamanhoInvalido() {
        Integer numDeCandidatos = -5;

        JSONFailureResponseWithArrayModel failureResponse = candidatoService.listarNumCandidatos(numDeCandidatos)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);


        Assertions.assertEquals(400, failureResponse.getStatus());
        Assertions.assertEquals("O tamanho não pode ser menor do que 1.", failureResponse.getMessage());
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 400 quando é passado qtd de candidatos como string")
    public void testListarCandidatosComTamanhoString() {
        String numDeCandidatos = "abc";

        var failureResponse = candidatoService.listarNumCandidatos(numDeCandidatos)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
