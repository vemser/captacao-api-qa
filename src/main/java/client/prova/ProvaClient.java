package client.prova;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.prova.ProvaCriacaoModel;
import specs.prova.ProvaSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class ProvaClient {

    public static final String ID_CANDIDATO_MARCAR_PROVA = "/{idCandidato}/marcar-prova";
    public static final String ID_CANDIDATO_DATA = "/{idCandidato}/data";
    public static final String ID_CANDIDATO_NOTA = "/{idCandidato}/nota";
    private static final String AUTHORIZATION = "Authorization";
    public static final String ID_CANDIDATO = "idCandidato";
    public static final String NOTA = "nota";

    public Response criarProva(Integer idCandidato, ProvaCriacaoModel prova) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(prova)
                .when()
                        .post(ID_CANDIDATO_MARCAR_PROVA)
                ;
    }

    public Response criarProvaSemAutenticacao(Integer idCandidato, ProvaCriacaoModel prova) {

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(prova)
                .when()
                        .post(ID_CANDIDATO_MARCAR_PROVA)
                ;
    }

    public Response atualizarDataProva(Integer idCandidato, ProvaCriacaoModel prova) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(prova)
                .when()
                        .put(ID_CANDIDATO_DATA)
                ;
    }

    public Response atualizarDataProvaSemAutenticacao(Integer idCandidato, ProvaCriacaoModel prova) {

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(prova)
                .when()
                        .put(ID_CANDIDATO_DATA)
                ;
    }

    public Response atualizarNotaProva(Integer idCandidato, Integer nota) {
        Auth.obterTokenComoAdmin();

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

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .queryParam(NOTA, nota)
                .when()
                        .put(ID_CANDIDATO_NOTA)
                ;
    }
}
