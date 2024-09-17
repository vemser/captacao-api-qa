package client.avaliacao;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.avaliacao.AvaliacaoCriacaoModel;
import specs.avaliacao.AvaliacaoSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class AvaliacaoClient {
    public static final String AUTHORIZATION = "Authorization";
    private static final String AVALIACAO = "/avaliacao";
    private static final String AVALIACAO_POR_ID = "/avaliacao/{idAvaliacao}";
    private static final String UPDATE_AVALIACAO = "/avaliacao/update/{idAvaliacao}";
    private static final String ID_AVALIACAO = "idAvaliacao";

    public Response cadastrarAvaliacao(AvaliacaoCriacaoModel avaliacao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(avaliacao)
                .when()
                        .post(AVALIACAO)
                ;
    }

    public Response cadastrarAvaliacaoSemAutenticacao(AvaliacaoCriacaoModel avaliacao) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(avaliacao)
                .when()
                        .post(AVALIACAO)
                ;
    }

    public Response deletarAvaliacao(Integer idAvaliacao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_AVALIACAO, idAvaliacao)
                .when()
                        .delete(AVALIACAO_POR_ID)
                ;
    }

    public Response deletarAvaliacaoSemAutenticacao(Integer idAvaliacao) {
        Auth.usuarioAluno();

        return
                given()
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .pathParam(ID_AVALIACAO, idAvaliacao)
                .when()
                        .delete(AVALIACAO_POR_ID)
                ;
    }

    public Response listarUltimaAvaliacao() {
        Auth.usuarioGestaoDePessoas();

        Integer pagina = 0;
        Integer tamanho = 1;
        Integer order = 1;

        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .header(AUTHORIZATION, "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imx1aXoucG9saWRvIiwianRpIjoiNTUiLCJjYXJnb3MiOltdLCJpYXQiOjE3MjY1MTYzMjksImV4cCI6MTcyOTEwODMyOX0._fwtBWfaAB5ebKXciv1BmMLXfom7ulm0oNxDeIua8oo")
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("sort", ID_AVALIACAO)
                        .queryParam("order", order)
                .when()
                        .get(AVALIACAO)
                ;
    }

    public Response listarUltimaAvaliacaoSemAutenticacao() {
        Auth.usuarioAluno();

        Integer pagina = 0;
        Integer tamanho = 1;
        Integer order = 1;

        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("sort", ID_AVALIACAO)
                        .queryParam("order", order)
                .when()
                        .get(AVALIACAO)
                ;
    }

    public Response atualizarAvaliacao(Integer idAvaliacao, AvaliacaoCriacaoModel avaliacao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_AVALIACAO, idAvaliacao)
                        .body(avaliacao)
                .when()
                        .put(UPDATE_AVALIACAO)
                ;
    }

    public Response atualizarAvaliacaoSemAutenticacao(Integer idAvaliacao, AvaliacaoCriacaoModel avaliacao) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_AVALIACAO, idAvaliacao)
                        .body(avaliacao)
                .when()
                        .put(UPDATE_AVALIACAO)
                ;
    }
}
