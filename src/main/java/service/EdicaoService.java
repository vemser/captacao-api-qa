package service;

import io.restassured.response.Response;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import utils.Auth;

import static io.restassured.RestAssured.given;

public class EdicaoService {
    public Response listarTodasAsEdicoes() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/edicao/listar-todas");

        return response;
    }

    public String listaEdicaoAtual() {

        String edicaoAtual =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/edicao/edicao-atual")
                .thenReturn()
                        .asString();

        return edicaoAtual;
    }

    public String listaEdicaoAtualSemAutenticacao() {

        String edicaoAtual =
                given()
                .when()
                        .get("/edicao/edicao-atual")
                        .thenReturn()
                        .asString();

        return edicaoAtual;
    }

    public EdicaoModel criarEdicao() {

        EdicaoModel edicaoCriada =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .post("/edicao/criar-edicao")
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .as(EdicaoModel.class);

        return edicaoCriada;
    }

    public Response criarEdicaoComNumEdicao(Integer numeroEdicao) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("numeroEdicao", numeroEdicao)
                .when()
                        .post("/edicao/criar-edicao");

        return response;
    }

    public Response criarEdicaoComNumEdicaoSemAutenticacao(Integer numeroEdicao) {

        Response response =
                given()
                        .queryParam("numeroEdicao", numeroEdicao)
                .when()
                        .post("/edicao/criar-edicao");

        return response;
    }

    public void deletarEdicao(Integer idEdicao) {

        var response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idEdicao", idEdicao)
                .when()
                        .delete("/edicao/delete-fisico/{idEdicao}")
                .then()
                        .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public Boolean verificaSeExistemEdicoesCadastradas() {
        String emptyResponse = "[]";

            var response =
                    given()
                            .header("Authorization", Auth.getToken())
                    .when()
                            .get("/edicao/listar-todas")
                    .then()
                            .statusCode(HttpStatus.SC_OK)
                            .extract().response();

        String responseBody = response.asString();

        if (responseBody.equals(emptyResponse)) {
            return false;
        } else {
            return true;
        }
    }

    public Response deletarEdicaoComResponse(Integer idEdicao) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idEdicao", idEdicao)
                .when()
                        .delete("/edicao/delete-fisico/{idEdicao}");

        return response;
    }

    public Response deletarEdicaoComResponseSemAutenticacao(Integer idEdicao) {

        Response response =
                given()
                        .pathParam("idEdicao", idEdicao)
                .when()
                        .delete("/edicao/delete-fisico/{idEdicao}");

        return response;
    }
}
