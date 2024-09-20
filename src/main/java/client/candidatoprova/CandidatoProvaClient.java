package client.candidatoprova;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.candidatoprova.CandidatoProvaModel;
import specs.prova.ProvaSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class CandidatoProvaClient {

    public static final String CRIAR_CANDIDATO_PROVA = "/candidato-prova/criar-candidato-prova";

    private static final String AUTHORIZATION = "Authorization";

    public Response cadastrarCandidatoProva(CandidatoProvaModel candidatoProva) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(candidatoProva)
                .when()
                        .post(CRIAR_CANDIDATO_PROVA)
                ;
    }

}
