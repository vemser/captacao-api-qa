package client.prova;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.prova.ProvaCriacaoModel;
import specs.prova.ProvaSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class ProvaClient {

    public static final String CRIAR_PROVA = "/criar-prova";
    private static final String AUTHORIZATION = "Authorization";

    public Response criarProva(ProvaCriacaoModel prova) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(prova)
                .when()
                        .post(CRIAR_PROVA)
                ;
    }

    public Response criarProvaSemAutenticacao(ProvaCriacaoModel prova) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(prova)
                .when()
                        .post(CRIAR_PROVA)
                ;
    }
}
