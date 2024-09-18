package client.edicao;

import client.auth.AuthClient;
import factory.edicao.EdicaoDataFactory;
import io.restassured.response.Response;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import specs.edicao.EdicaoSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class EdicaoClient {
    public static final String AUTHORIZATION = "Authorization";
    public static final String EDICAO_LISTAR_TODAS = "/edicao/listar-todas";
    public static final String EDICAO_EDICAO_ATUAL = "/edicao/edicao-atual";
    public static final String EDICAO_CRIAR_EDICAO = "/edicao/criar-edicao";
    public static final String EDICAO_DELETE_FISICO_ID_EDICAO = "/edicao/delete-fisico/{idEdicao}";
    public static final String ID_EDICAO = "idEdicao";
    public static final String NUMERO_EDICAO = "numeroEdicao";

    public Response listarTodasAsEdicoes() {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(EdicaoSpecs.edicaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(EDICAO_LISTAR_TODAS)
                ;
    }

    public String listaEdicaoAtualSemAutenticacao() {
        Auth.usuarioAluno();
        return
                given()
                        .spec(EdicaoSpecs.edicaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(EDICAO_EDICAO_ATUAL)
                        .thenReturn()
                        .asString()
                ;
    }

    public EdicaoModel criarEdicao(EdicaoModel edicao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(EdicaoSpecs.edicaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(edicao)
                .when()
                        .post(EDICAO_CRIAR_EDICAO)
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .as(EdicaoModel.class)
                ;
    }

    public Response cadastrarEdicao(EdicaoModel model) {
        Auth.usuarioGestaoDePessoas();

        return
                given()

                        .header(AUTHORIZATION, AuthClient.getToken())
                        .spec(EdicaoSpecs.edicaoReqSpec())
                        .body(model)
                .when()
                        .post(EDICAO_CRIAR_EDICAO);
    }

    public Response criarEdicaoComNumEdicaoSemAutenticacao(Integer numeroEdicao) {
        Auth.usuarioAluno();
        return
                given()
                        .spec(EdicaoSpecs.edicaoReqSpec())
                        .queryParam(NUMERO_EDICAO, numeroEdicao)
                .when()
                        .post(EDICAO_CRIAR_EDICAO)
                ;
    }

    public Response deletarEdicao(Integer idEdicao) {
        Auth.usuarioGestaoDePessoas();

        return given()
                        .spec(EdicaoSpecs.edicaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_EDICAO, idEdicao)
                .when()
                        .delete(EDICAO_DELETE_FISICO_ID_EDICAO);
    }

    public Response deletarEdicaoSemAutenticacao(Integer idEdicao) {
        Auth.usuarioAluno();

        return given()
                .spec(EdicaoSpecs.edicaoReqSpec())
                .pathParam(ID_EDICAO, idEdicao)
                .when()
                .delete(EDICAO_DELETE_FISICO_ID_EDICAO);
    }

}
