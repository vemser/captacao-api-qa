package br.com.dbccompany.vemser.tests.entrevista;

import client.EntrevistaClient;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Endpoint de listagem de entrevistas por trilha")
class ListarEntrevistaPorTrilhaTest {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final String PATH_SCHEMA_LISTAR_ENTREVISTAS = "schemas/entrevista/listar_entrevistas.json";
    public static final String TRILHA_VALIDA = "QA";
    public static final String TRILHA_NAO_EXISTENTE = "-*/-*/-*/-*/-*/";

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar entrevistas por trilha")
    @Tag("Contract")
    public void testValidarContratoListarEntrevistasPorTrilha() {

        entrevistaClient.listarTodasAsEntrevistasPorTrilha(TRILHA_VALIDA)
                .then()
                	.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_ENTREVISTAS))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando lista entrevistas de trilhas existentes")
    @Tag("Regression")
    void testListarEntrevistasPorTrilhaComSucesso() {

        entrevistaClient.listarTodasAsEntrevistasPorTrilha(TRILHA_VALIDA)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel[].class);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 404 quando lista entrevistas de trilhas não existentes")
    @Tag("Regression")
    void testListarEntrevistasPorTrilhaNaoExistente() {

       entrevistaClient.listarTodasAsEntrevistasPorTrilha(TRILHA_NAO_EXISTENTE)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(equalTo("[]"));
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 403 quando lista entrevistas de trilhas sem autenticação")
    @Tag("Regression")
    void testListarEntrevistasPorTrilhaSemAutenticacao() {

        entrevistaClient.listarTodasAsEntrevistasPorTrilhaSemAutenticacao(TRILHA_VALIDA)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
