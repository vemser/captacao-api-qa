package br.com.dbccompany.vemser.tests.trilha;

import client.trilha.TrilhaClient;
import factory.trilha.TrilhaDataFactory;
import io.restassured.response.Response;
import models.trilha.TrilhaModel;
import models.trilha.TrilhaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastro de trilhas")
class TrilhaFuncionalTest {
    TrilhaClient trilhaClient = new TrilhaClient();
    private static final String PATH_SCHEMA_CADASTRAR_TRILHA = "schemas/trilha/post_trilha.json";
    private static final String PATH_SCHEMA_LISTAR_TRILHAS = "schemas/trilha/listar_trilhas.json";

    @Test
    @DisplayName("Cenário 1: Validação de contrato de cadastrar trilha")
    public void testValidarContratoCadastrarTrilha() {
        TrilhaModel trilha = TrilhaDataFactory.trilhaValida();

        trilhaClient.cadastrarTrilha(trilha)
            .then()
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_CADASTRAR_TRILHA))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Validação de contrato de listar trilhas")
    public void testValidarContratoListarTrilhas() {

        trilhaClient.listarTodasAsTrilhas()
            .then()
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_TRILHAS))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 201 quando cadastra trilha com sucesso")
    void testCadastroDeTrilhaComSucesso(){
        TrilhaModel trilha = TrilhaDataFactory.trilhaValida();

        TrilhaResponse trilhaResponse = trilhaClient.cadastrarTrilha(trilha)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(TrilhaResponse.class);

        // Convertendo o idTrilha de String para Integer
        Integer idTrilha = Integer.parseInt(String.valueOf(trilhaResponse.getIdTrilha()));

        Response response = trilhaClient.deletarTrilhaPorId(idTrilha);

        response.then().statusCode(204);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 200 quando lista as trilhas com sucesso")
    void testListarTrilhasComSucesso(){
        List<TrilhaModel> trilhas = trilhaClient.listarTodasAsTrilhas()
                .then()
                .log().all()
                .statusCode(200)
                .extract().jsonPath().getList(".", TrilhaModel.class);

        Assertions.assertNotNull(trilhas);
        Assertions.assertFalse(trilhas.isEmpty());
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 204 quando deleta trilha com sucesso")
    void testDeletarTrilhaComSucesso() {
        TrilhaModel trilha = TrilhaDataFactory.trilhaValida();

        TrilhaResponse trilhaResponse = trilhaClient.cadastrarTrilha(trilha)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(TrilhaResponse.class);

        trilhaClient.deletarTrilhaPorId(trilhaResponse.getIdTrilha());
    }
}
