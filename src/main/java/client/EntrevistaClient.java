package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.entrevista.EntrevistaCriacaoModel;
import utils.Auth;

import static io.restassured.RestAssured.given;

public class EntrevistaClient {

    public Response cadastrarEntrevista(EntrevistaCriacaoModel entrevista) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(entrevista)
                .when()
                        .post("/entrevista/marcar-entrevista");

        return response;
    }

    public Response cadastrarEntrevistaSemAutenticacao(EntrevistaCriacaoModel entrevista) {

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(entrevista)
                .when()
                        .post("/entrevista/marcar-entrevista");

        return response;
    }

    public Response listarTodasAsEntrevistas() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/entrevista");

        return response;
    }

    public Response listarTodasAsEntrevistasPorEmail(String emailDoCandidato) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("email", emailDoCandidato)
                .when()
                        .get("/entrevista/buscar-entrevista-email-candidato/{email}");

        return response;
    }

    public Response listarTodasAsEntrevistasPorEmailSemAutenticacao(String emailDoCandidato) {

        Response response =
                given()
                        .pathParam("email", emailDoCandidato)
                .when()
                        .get("/entrevista/buscar-entrevista-email-candidato/{email}");

        return response;
    }

    public Response listarTodasAsEntrevistasPorTrilha(String trilha) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("trilha", trilha)
                .when()
                        .get("/entrevista/por-trilha");

        return response;
    }

    public Response listarTodasAsEntrevistasPorTrilhaSemAutenticacao(String trilha) {

        Response response =
                given()
                        .queryParam("trilha", trilha)
                .when()
                        .get("/entrevista/por-trilha");

        return response;
    }

    public Response listarTodasAsEntrevistasPorMes(Integer anoEntrevista, Integer mesEntrevista) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("mes", mesEntrevista)
                        .queryParam("ano", anoEntrevista)
                .when()
                        .get("/entrevista/listar-por-mes");

        return response;
    }

    public Response listarTodasAsEntrevistasPorMesSemAutenticacao(Integer anoEntrevista, Integer mesEntrevista) {

        Response response =
                given()
                        .queryParam("mes", mesEntrevista)
                        .queryParam("ano", anoEntrevista)
                .when()
                        .get("/entrevista/listar-por-mes");

        return response;
    }

    public Response listarTodasAsEntrevistasSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/entrevista");

        return response;
    }

    public Response atualizarEntrevista(Integer idEntrevista, String status, EntrevistaCriacaoModel dadosAtualizados) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idEntrevista", idEntrevista)
                        .queryParam("legenda", status)
                        .contentType(ContentType.JSON)
                        .body(dadosAtualizados)
                .when()
                        .put("/entrevista/atualizar-entrevista/{idEntrevista}");

        return response;
    }

    public Response atualizarEntrevistaSemAutenticacao(Integer idEntrevista, String status, EntrevistaCriacaoModel dadosAtualizados) {

        Response response =
                given()
                        .pathParam("idEntrevista", idEntrevista)
                        .queryParam("legenda", status)
                        .contentType(ContentType.JSON)
                        .body(dadosAtualizados)
                .when()
                        .put("/entrevista/atualizar-entrevista/{idEntrevista}");

        return response;
    }

    public Response deletarEntrevistaPorId(Integer idEntrevista) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idEntrevista", idEntrevista)
                .when()
                        .delete("/entrevista/{idEntrevista}");

        return response;
    }

    public Response deletarEntrevistaPorIdSemAutenticacao(Integer idEntrevista) {

        Response response =
                given()
                        .pathParam("idEntrevista", idEntrevista)
                .when()
                        .delete("/entrevista/{idEntrevista}");

        return response;
    }
}
