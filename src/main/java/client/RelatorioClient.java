package client;

import io.restassured.response.Response;
import utils.Auth;

import static io.restassured.RestAssured.given;

public class RelatorioClient {

    public Response listarCandidatosPcd() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-pcd");

        return response;
    }

    public Response listarCandidatosPcdSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-pcd");

        return response;
    }

    public Response listarCandidatosNeurodiversidade() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-neurodiversidade");

        return response;
    }

    public Response listarCandidatosNeurodiversidadeSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-neurodiversidade");

        return response;
    }

    public Response listarCandidatosGenero() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-genero");

        return response;
    }

    public Response listarCandidatosGeneroSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-genero");

        return response;
    }

    public Response listarCandidatosEtnia() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-etnia");

        return response;
    }

    public Response listarCandidatosEtniaSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-etnia");

        return response;
    }

    public Response listarCandidatosEstado() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-estado");

        return response;
    }

    public Response listarCandidatosEstadoSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-estado");

        return response;
    }

    public Response listarCandidatosEdicao() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-edicao");

        return response;
    }

    public Response listarCandidatosEdicaoSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/relatorios/quantidade-de-pessoas-inscritas-por-edicao");

        return response;
    }
}
