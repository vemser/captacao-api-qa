package client.relatorio;

import client.auth.AuthClient;
import io.restassured.response.Response;
import specs.relatorio.RelatorioSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class RelatorioClient {

    private static final String AUTHORIZATION = "Authorization";
    public static final String RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_PCD = "/relatorios/quantidade-de-pessoas-inscritas-por-pcd";
    public static final String RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_NEURODIVERSIDADE = "/relatorios/quantidade-de-pessoas-inscritas-por-neurodiversidade";
    public static final String RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_GENERO = "/relatorios/quantidade-de-pessoas-inscritas-por-genero";
    public static final String RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_ESTADO = "/relatorios/quantidade-de-pessoas-inscritas-por-estado";
    public static final String RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_EDICAO = "/relatorios/quantidade-de-pessoas-inscritas-por-edicao";

    public Response listarCandidatosPcd(String nomeEdicao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("nomeEdicao", nomeEdicao)
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_PCD)
                ;
    }

    public Response listarCandidatosNeurodiversidade(String nomeEdicao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("nomeEdicao", nomeEdicao)
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_NEURODIVERSIDADE)
                ;
    }

    public Response listarCandidatosGenero(String nomeEdicao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("nomeEdicao", nomeEdicao)
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_GENERO)
                ;
    }

    public Response listarCandidatosGeneroSemAutenticacao() {
        Auth.usuarioAluno();

        return
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_GENERO)
                ;
    }

    public Response listarCandidatosEstado(String nomeEdicao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("nomeEdicao", nomeEdicao)
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_ESTADO)
                ;
    }

    public Response listarCandidatosEstadoSemAutenticacao() {
        Auth.usuarioAluno();

        Response response =
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_ESTADO)
                ;

        return response;
    }

    public Response listarCandidatosEdicao(String nomeEdicao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("nomeEdicao", nomeEdicao)
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_EDICAO)
                ;
    }

    public Response listarCandidatosEdicaoSemAutenticacao() {
        Auth.usuarioAluno();

        return
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_EDICAO)
                ;
    }

    public Response listarCandidatosNeurodiversidadeSemAutenticacao() {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(RelatorioSpecs.relatorioReqSpec())
                .when()
                        .get(RELATORIOS_QUANTIDADE_DE_PESSOAS_INSCRITAS_POR_NEURODIVERSIDADE)
                ;
    }
}
