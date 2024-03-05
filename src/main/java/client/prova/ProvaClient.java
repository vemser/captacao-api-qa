package client.prova;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaEditarModel;
import specs.prova.ProvaSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class ProvaClient {

    public static final String CRIAR_PROVA = "/prova/criar-prova";
    // Endpoints de edição
    public static final String EDITAR_PROVA = "/prova/editar-prova/{idProva}";

    // Endpoints de obtenção de dados
    public static final String PEGAR_PROVA_POR_ID = "/prova/pegar-prova/{idProva}";
    public static final String LISTAR_PROVAS = "/prova/listar-provas";
    public static final String LISTAR_PROVAS_PALAVRAS_CHAVE = "/prova/listar-provas-palavra-chave";
    public static final String LISTAR_PROVAS_POR_DATA = "/prova/listar-provas-periodo-datas";
    public static final String LISTAR_PROVAS_EDICAO = "/prova/listar-provas-edicao";
    public static final String LISTAR_PROVA_VERSAO = "/prova/listar-prova-versao";

    // Endpoint de deleção lógica
    public static final String DELETE_LOGICO_PROVA = "/prova/delete-logico/{idProva}";
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

    public Response editarQuestoesProva(Integer id, ProvaEditarModel provaEditarModel) {
        Auth.usuarioInstrutor();

        return given()
                    .spec(ProvaSpecs.provaReqSpec())
                    .header(AUTHORIZATION, AuthClient.getToken())
                    .pathParam("idProva", id)
                    .body(provaEditarModel)
                .when()
                    .put(EDITAR_PROVA)
                ;
    }

    public Response editarDuracaoProva(Integer id, ProvaEditarModel prova) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idProva", id)
                        .body(prova)
                .when()
                        .put(EDITAR_PROVA)
                ;
    }

    public Response editarDadosProva(Integer id, ProvaEditarModel prova) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idProva", id)
                        .body(prova)
                .when()
                        .put(EDITAR_PROVA)
                ;
    }

    public Response pegarProva(Integer id) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idProva", id)
                        .queryParam("idProva", id)
                .when()
                        .get(PEGAR_PROVA_POR_ID)
                ;
    }

    public Response listarProva() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(LISTAR_PROVAS)
                ;
    }

    public Response listarProvasPorPalavraChave(String keyword, Integer page, Integer size) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParams("palavraChave", keyword, "page", page, "size", size)
                    .when()
                        .get(LISTAR_PROVAS_PALAVRAS_CHAVE)
                ;
    }

    public Response listarProvasPorEdicao(Integer page, Integer size, Integer id) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParams("page", page, "size", size, "idEdicao", id)
                        .when()
                        .get(LISTAR_PROVAS_EDICAO)
                ;
    }

    public Response listarProvasPorVersao(Integer page, Integer size, Integer versao) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParams("page", page, "size", size, "versao", versao)
                        .when()
                        .get(LISTAR_PROVA_VERSAO)
                ;
    }

    public Response listarProvasPorDatas(Integer page, Integer size, String dataInicio, String dataFinal) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParams("page", page, "size", size, "dataInicio", dataInicio, "dataFinal", dataFinal)
                        .when()
                        .get(LISTAR_PROVAS_POR_DATA)
                ;
    }

    public Response deletarProva(Integer id) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idProva", id)
                        .when()
                        .delete(DELETE_LOGICO_PROVA)
                ;
    }

    public Response deletarProvaSemAutenticacao(Integer id) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idProva", id)
                        .when()
                        .delete(DELETE_LOGICO_PROVA)
                ;
    }
}
