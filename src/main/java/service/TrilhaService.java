package service;

import dataFactory.TrilhaDataFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.trilha.TrilhaApenasNomeModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import utils.Auth;

import static io.restassured.RestAssured.given;

public class TrilhaService {

    TrilhaDataFactory trilhaDataFactory = new TrilhaDataFactory();

    public Response listarTodasAsTrilhas() {

        Response response =
                given()
                .when()
                        .get("/trilha/listar");

        return response;
    }

    public Response listarTodasAsTrilhasSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/trilha/listar");

        return response;
    }

    public TrilhaModel criarTrilha() {

        TrilhaModel trilhaCriada =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(trilhaDataFactory.trilhaValidaApenasNome())
                .when()
                        .post("/trilha")
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel.class);

        return trilhaCriada;
    }

    public Response criarTrilhaPassandoNome(TrilhaApenasNomeModel trilha) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(trilha)
                .when()
                        .post("/trilha");

        return response;
    }

    public Response criarTrilhaPassandoNomeSemAutenticacao(TrilhaApenasNomeModel trilha) {

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(trilha)
                .when()
                        .post("/trilha");

        return response;
    }

    public Response deletarTrilha(Integer idTrilha) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idTrilha", idTrilha)
                .when()
                        .delete("/trilha/deletar/{idTrilha}");

        return response;
    }
}
