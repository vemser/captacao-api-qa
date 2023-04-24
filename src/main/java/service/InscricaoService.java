package service;

import io.restassured.response.Response;
import utils.Auth;

import static io.restassured.RestAssured.given;

public class InscricaoService {

    public Response cadastrarInscricao(Integer idCandidato) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("idCandidato", idCandidato)
                .when()
                        .post("/inscricao/cadastro");

        return response;
    }

    public Response cadastrarInscricaoSemAutenticacao(Integer idCandidato) {

        Response response =
                given()
                        .queryParam("idCandidato", idCandidato)
                .when()
                        .post("/inscricao/cadastro");

        return response;
    }

    public Response deletarInscricao(Integer idInscricao) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idInscricao", idInscricao)
                .when()
                        .delete("/inscricao/{idInscricao}");

        return response;
    }

    public Response deletarInscricaoSemAutenticacao(Integer idInscricao) {

        Response response =
                given()
                        .pathParam("idInscricao", idInscricao)
                .when()
                        .delete("/inscricao/{idInscricao}");

        return response;
    }

    public Response listaUltimaInscricao() {
        Integer pagina = 0;
        Integer tamanho = 1;
        String sort = "idInscricao";
        Integer order = 1;

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("sort", sort)
                        .queryParam("order", order)
                .when()
                        .get("/inscricao");

        return response;
    }

    public Response listarInscricaoPorId(Integer idInscricao) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("id", idInscricao)
                .when()
                        .get("/inscricao/find-by-idInscricao");

        return response;
    }

    public Response listarInscricaoPorIdSemAutenticacao(Integer idInscricao) {

        Response response =
                given()
                        .queryParam("id", idInscricao)
                .when()
                        .get("/inscricao/find-by-idInscricao");

        return response;
    }
}
