package client.prova;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.prova.ProvaCriacaoModel;
import specs.prova.ProvaSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class ProvaClient {

    public static final String CRIAR_PROVA = "/criar-prova";
    public static final String ID_CANDIDATO_NOTA = "/candidato/nota-prova/{idCandidato}";
    private static final String AUTHORIZATION = "Authorization";
    public static final String ID_CANDIDATO = "idCandidato";
    public static final String NOTA = "nota";

    public Response criarProva(Integer id, ProvaCriacaoModel prova) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, id)
                        .body(prova)
                .when()
                        .post(CRIAR_PROVA)
                ;
    }

    public Response criarProvaSemAutenticacao(Integer idCandidato, ProvaCriacaoModel prova) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(prova)
                .when()
                        .post(CRIAR_PROVA)
                ;
    }

    public Response atualizarNotaProva(Integer idCandidato, Integer nota) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .queryParam(NOTA, nota)
                .when()
                        .put(ID_CANDIDATO_NOTA)
                ;
    }

    public Response atualizarNotaProvaSemAutenticacao(Integer idCandidato, Integer nota) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .queryParam(NOTA, nota)
                .when()
                        .put(ID_CANDIDATO_NOTA)
                ;
    }
}
