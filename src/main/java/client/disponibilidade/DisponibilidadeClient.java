package client.disponibilidade;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.disponibilidade.DisponibilidadeModel;
import specs.entrevista.DisponibilidadeSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class DisponibilidadeClient {

    public static final String AUTHORIZATION = "Authorization";
    public static final String CRIAR_DISPONIBILIDADE = "/disponibilidade/criar-disponibilidade";
    public static final String LISTAR_TODAS_DISPONIBILIDADES = "/disponibilidade/todas-disponibilidades";

    public Response cadastrarDisponibilidade(DisponibilidadeModel disponibilidade) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(DisponibilidadeSpecs.disponibilidadeReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(disponibilidade)
                .when()
                        .post(CRIAR_DISPONIBILIDADE);
    }

    public Response listarDisponibilidades() {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(DisponibilidadeSpecs.disponibilidadeReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(LISTAR_TODAS_DISPONIBILIDADES);
    }

    public Response listarDisponibilidadesSemAutenticacao() {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(DisponibilidadeSpecs.disponibilidadeReqSpec())
                .when()
                        .get(LISTAR_TODAS_DISPONIBILIDADES);
    }
}
