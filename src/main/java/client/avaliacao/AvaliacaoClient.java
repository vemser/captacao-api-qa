package client.avaliacao;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.avaliacao.AvaliacaoCriacaoModel;
import org.apache.commons.lang3.StringUtils;
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
                        .queryParam("token", AuthClient.getToken())
                        .body(avaliacao)
                .when()
                        .post(AVALIACAO);
    }

    public Response cadastrarAvaliacaoSemAutenticacao(AvaliacaoCriacaoModel avaliacao) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .body(avaliacao)
                .when()
                        .post(AVALIACAO);
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

    public Response listarTodaAvaliacao(boolean condicaoInserirTokenValido) {
        String token = StringUtils.EMPTY;
        if(condicaoInserirTokenValido){
            Auth.usuarioGestaoDePessoas();
            token = AuthClient.getToken();
        }
        return
                given()
                        .spec(AvaliacaoSpecs.avaliacaoReqSpec())
                        .header(AUTHORIZATION, token)
                        .queryParam("pagina", 0)
                        .queryParam("tamanho", 1)
                        .queryParam("sort", ID_AVALIACAO)
                        .queryParam("order", 0)
                .when()
                        .get(AVALIACAO);
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
                        .pathParam(ID_AVALIACAO, idAvaliacao)
                        .body(avaliacao)
                .when()
                        .put(UPDATE_AVALIACAO);
    }
}
