package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.prova.ProvaCriacaoModel;
import utils.Auth;

import static io.restassured.RestAssured.given;

public class ProvaClient {

    public Response criarProva(Integer idCandidato, ProvaCriacaoModel prova) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(prova)
                .when()
                        .post("/{idCandidato}/marcar-prova");

        return response;
    }

    public Response criarProvaSemAutenticacao(Integer idCandidato, ProvaCriacaoModel prova) {

        Response response =
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(prova)
                .when()
                        .post("/{idCandidato}/marcar-prova");

        return response;
    }

    public Response atualizarDataProva(Integer idCandidato, ProvaCriacaoModel prova) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(prova)
                .when()
                        .put("/{idCandidato}/data");

        return response;
    }

    public Response atualizarDataProvaSemAutenticacao(Integer idCandidato, ProvaCriacaoModel prova) {

        Response response =
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(prova)
                .when()
                        .put("/{idCandidato}/data");

        return response;
    }

    public Response atualizarNotaProva(Integer idCandidato, Integer nota) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idCandidato", idCandidato)
                        .queryParam("nota", nota)
                .when()
                        .put("/{idCandidato}/nota");

        return response;
    }

    public Response atualizarNotaProvaSemAutenticacao(Integer idCandidato, Integer nota) {

        Response response =
                given()
                        .pathParam("idCandidato", idCandidato)
                        .queryParam("nota", nota)
                .when()
                        .put("/{idCandidato}/nota");

        return response;
    }
}
