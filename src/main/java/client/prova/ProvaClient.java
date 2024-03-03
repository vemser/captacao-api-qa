package client.prova;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.prova.ProvaCriacaoModel;
import specs.prova.ProvaSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class ProvaClient {

    public static final String CRIAR_PROVA = "/criar-prova";
    // Endpoints de edição
    public static final String EDITAR_QUESTOES_PROVA = "/prova/editar-questoes-prova";
    public static final String EDITAR_DURACAO_PROVA = "/prova/editar-duracao-prova";
    public static final String EDITAR_DADOS_PROVA = "/prova/editar-dados-prova";

    // Endpoints de obtenção de dados
    public static final String PEGAR_PROVA = "/prova/pegar-prova";
    public static final String LISTAR_PROVAS = "/prova/listar-provas";
    public static final String LISTAR_PROVAS_PALAVRAS_CHAVE = "/prova/listar-provas-palavras-chave";
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

    public Response editarQuestoesProva(Integer id, ProvaCriacaoModel prova) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idProva", id)
                        .body(prova)
                        .when()
                        .put(EDITAR_QUESTOES_PROVA)
                ;
    }

    public Response editarDuracaoProva(Integer id, ProvaCriacaoModel prova) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idProva", id)
                        .body(prova)
                        .when()
                        .put(EDITAR_DURACAO_PROVA)
                ;
    }

    public Response editarDadosProva(Integer id, ProvaCriacaoModel prova) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idProva", id)
                        .body(prova)
                        .when()
                        .put(EDITAR_DADOS_PROVA)
                ;
    }

    public Response pegarProva() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(PEGAR_PROVA)
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

    public Response listarProvasPorPalavraChave() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(LISTAR_PROVAS_PALAVRAS_CHAVE)
                ;
    }

    public Response listarProvasPorEdicao() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(LISTAR_PROVAS_EDICAO)
                ;
    }

    public Response listarProvasPorVersao() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(LISTAR_PROVA_VERSAO)
                ;
    }

    public Response deletarProva() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(ProvaSpecs.provaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(DELETE_LOGICO_PROVA)
                ;
    }
}
