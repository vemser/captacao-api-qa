package client.disponibilidade;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.disponibilidade.DisponibilidadeModel;
import org.apache.commons.lang3.StringUtils;
import specs.entrevista.DisponibilidadeSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class DisponibilidadeClient {

    public static final String AUTHORIZATION = "Authorization";
    public static final String CRIAR_DISPONIBILIDADE = "/disponibilidade/criar-disponibilidade";
    public static final String LISTAR_TODAS_DISPONIBILIDADES = "/disponibilidade/todas-disponibilidades";
    public static final String DELETE_DISPONIBILIDADE = "/disponibilidade/delete";

    public Response cadastrarDisponibilidade(DisponibilidadeModel disponibilidade) {
        Auth.usuarioGestaoDePessoas();

        Response response =
                given()
                        .spec(DisponibilidadeSpecs.disponibilidadeReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(disponibilidade)
                .when()
                        .post(CRIAR_DISPONIBILIDADE);
        return response;
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

    public Response deletarDisponibilidade(String id, boolean isCondicaoInserirTokenValido){
        return
                given()
                    .spec(DisponibilidadeSpecs.disponibilidadeReqSpec())
                    .header(AUTHORIZATION, inserirToken(isCondicaoInserirTokenValido))
                    .pathParam("idDisponibilidade", id)
                .when()
                    .delete(DELETE_DISPONIBILIDADE + "/{idDisponibilidade}");
    }
    private String inserirToken(boolean isCondicaoInserirTokenValido){
        String token = StringUtils.EMPTY;
        if(isCondicaoInserirTokenValido){
            Auth.usuarioGestaoDePessoas();
            token = AuthClient.getToken();
        }
        return token;
    }
}
