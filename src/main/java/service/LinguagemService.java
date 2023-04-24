package service;

import dataFactory.LinguagemDataFactory;
import io.restassured.response.Response;
import models.linguagem.LinguagemModel;
import org.apache.http.HttpStatus;
import utils.Auth;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class LinguagemService {

    private static LinguagemDataFactory linguagemDataFactory = new LinguagemDataFactory();

    public LinguagemModel criarLinguagemBase() {

        LinguagemModel linguagemCriada =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("nome", linguagemDataFactory.linguagemValida().getNome())
                .when()
                        .post("/linguagem")
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .as(LinguagemModel.class);

        return linguagemCriada;
    }

    public Response criarLinguagemPassandoNome(String nomeDaLinguagem) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("nome", nomeDaLinguagem)
                .when()
                        .post("/linguagem");

        return response;
    }

    public Response criarLinguagemPassandoNomeSemAutenticacao(String nomeDaLinguagem) {

        Response response =
                given()
                        .queryParam("nome", nomeDaLinguagem)
                .when()
                        .post("/linguagem");

        return response;
    }

    public Response deletarLinguagemPorId(Integer idLinguagem) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idLinguagem", idLinguagem)
                .when()
                        .delete("/linguagem/delete-fisico/{idLinguagem}");

        return response;
    }

    public Response deletarLinguagemPorIdSemAutenticacao(Integer idLinguagem) {

        Response response =
                given()
                        .pathParam("idLinguagem", idLinguagem)
                .when()
                        .delete("/linguagem/delete-fisico/{idLinguagem}");

        return response;
    }

    public Response listarLinguagens() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/linguagem");

        return response;
    }

    public Response listarLinguagensSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/linguagem");

        return response;
    }

    public LinguagemModel retornarPrimeiraLinguagemCadastrada() {

        LinguagemModel[] response =
                given()
                .when()
                        .get("/linguagem")
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(LinguagemModel[].class);

        List<LinguagemModel> listaDeLinguagens = Arrays.stream(response).toList();

        LinguagemModel primeiraLinguagem = listaDeLinguagens.get(0);

        return primeiraLinguagem;
    }
}
