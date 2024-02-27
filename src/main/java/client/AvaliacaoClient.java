package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.avaliacao.AvaliacaoCriacaoModel;
import utils.Auth;

import static io.restassured.RestAssured.given;

public class AvaliacaoClient {

    public Response cadastrarAvaliacao(AvaliacaoCriacaoModel avaliacao) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(avaliacao)
                .when()
                        .post("/avaliacao");

        return response;
    }

    public Response cadastrarAvaliacaoSemAutenticacao(AvaliacaoCriacaoModel avaliacao) {

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(avaliacao)
                .when()
                        .post("/avaliacao");

        return response;
    }

    public Response deletarAvaliacao(Integer idAvaliacao) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idAvaliacao", idAvaliacao)
                .when()
                        .delete("/avaliacao/{idAvaliacao}");

        return response;
    }

    public Response deletarAvaliacaoSemAutenticacao(Integer idAvaliacao) {

        Response response =
                given()
                        .pathParam("idAvaliacao", idAvaliacao)
                .when()
                        .delete("/avaliacao/{idAvaliacao}");

        return response;
    }

    public Response listarUltimaAvaliacao() {
        Integer pagina = 0;
        Integer tamanho = 1;
        String sort = "idAvaliacao";
        Integer order = 1;

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("sort", sort)
                        .queryParam("order", order)
                .when()
                        .get("/avaliacao");

        return response;
    }

    public Response listarUltimaAvaliacaoSemAutenticacao() {
        Integer pagina = 0;
        Integer tamanho = 1;
        String sort = "idAvaliacao";
        Integer order = 1;

        Response response =
                given()
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("sort", sort)
                        .queryParam("order", order)
                .when()
                        .get("/avaliacao");

        return response;
    }

    public Response atualizarAvaliacao(Integer idAvaliacao, AvaliacaoCriacaoModel avaliacao) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idAvaliacao", idAvaliacao)
                        .contentType(ContentType.JSON)
                        .body(avaliacao)
                .when()
                        .put("/avaliacao/update/{idAvaliacao}");

        return response;
    }

    public Response atualizarAvaliacaoSemAutenticacao(Integer idAvaliacao, AvaliacaoCriacaoModel avaliacao) {

        Response response =
                given()
                        .pathParam("idAvaliacao", idAvaliacao)
                        .contentType(ContentType.JSON)
                        .body(avaliacao)
                .when()
                        .put("/avaliacao/update/{idAvaliacao}");

        return response;
    }
}
