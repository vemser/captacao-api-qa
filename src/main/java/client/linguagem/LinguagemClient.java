package client.linguagem;

import client.auth.AuthClient;
import factory.linguagem.LinguagemDataFactory;
import io.restassured.response.Response;
import models.linguagem.LinguagemModel;
import specs.linguagem.LinguagemSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class LinguagemClient {
    private static final String AUTHORIZATION = "Authorization";
    public static final String LINGUAGEM = "/linguagem";
    public static final String LINGUAGEM_DELETE_FISICO_ID_LINGUAGEM = "/linguagem/delete-fisico/{idLinguagem}";
    public static final String NOME = "nome";
    public static final String ID_LINGUAGEM = "idLinguagem";

    public Response criarLinguagemPassandoNome(String nomeDaLinguagem) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(LinguagemSpecs.linguagemReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(NOME, nomeDaLinguagem)
                .when()
                        .post(LINGUAGEM)
                ;
    }

    public Response criarLinguagemPassandoNomeSemAutenticacao(String nomeDaLinguagem) {

        return
                given()
                        .spec(LinguagemSpecs.linguagemReqSpec())
                        .queryParam(NOME, nomeDaLinguagem)
                .when()
                        .post(LINGUAGEM)
                ;
    }

    public Response deletarLinguagemPorId(Integer idLinguagem) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(LinguagemSpecs.linguagemReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_LINGUAGEM, idLinguagem)
                .when()
                        .delete(LINGUAGEM_DELETE_FISICO_ID_LINGUAGEM)
                ;
    }

    public Response deletarLinguagemPorIdSemAutenticacao(Integer idLinguagem) {

        return
                given()
                        .spec(LinguagemSpecs.linguagemReqSpec())
                        .pathParam(ID_LINGUAGEM, idLinguagem)
                .when()
                        .delete(LINGUAGEM_DELETE_FISICO_ID_LINGUAGEM)
                ;
    }

    public Response listarLinguagens() {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(LinguagemSpecs.linguagemReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(LINGUAGEM)
                ;
    }

    public Response listarLinguagensSemAutenticacao() {

        return
                given()
                        .spec(LinguagemSpecs.linguagemReqSpec())
                .when()
                        .get(LINGUAGEM)
                ;
    }

    public LinguagemModel retornarPrimeiraLinguagemCadastrada() {
        Auth.obterTokenComoAdmin();

        Response response =
                given()
                        .spec(LinguagemSpecs.linguagemReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(LINGUAGEM)
                        .thenReturn()
                ;

        LinguagemModel[] linguagens = response.as(LinguagemModel[].class);
        return linguagens[0];
    }
}