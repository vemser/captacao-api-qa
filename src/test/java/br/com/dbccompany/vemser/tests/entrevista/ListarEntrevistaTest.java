package br.com.dbccompany.vemser.tests.entrevista;

import client.edicao.EdicaoClient;
import client.entrevista.EntrevistaClient;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de entrevistas")
class ListarEntrevistaTest {
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final String PATH_SCHEMA_LISTAR_ENTREVISTAS = "schemas/entrevista/listar_entrevistas.json";
    private static final EdicaoClient edicaoClient = new EdicaoClient();

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar entrevistas")
    @Tag("Contract")
    public void testValidarContratoListarEntrevistas() {

        String edicao = edicaoClient.listaEdicaoAtualAutenticacao()
                .then()
                .extract().asString();

        entrevistaClient.listarTodasAsEntrevistas(edicao)
                .then()
					.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_ENTREVISTAS))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando lista as entrevistas cadastradas com sucesso")
    @Tag("Regression")
    void testListarEntrevistasCadastradasComSucesso() {

        String edicao = edicaoClient.listaEdicaoAtualAutenticacao()
                .then()
                .extract().asString();

        var listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistas(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel[].class);

        Assertions.assertNotNull(listaDeEntrevistas);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 quando lista as entrevistas sem estar autenticado")
    @Tag("Regression")
    void testListarEntrevistasSemAutenticacao() {

        entrevistaClient.listarTodasAsEntrevistasSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
