package client.inscricao;

import client.auth.AuthClient;
import io.restassured.response.Response;
import specs.inscricao.InscricaoSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class InscricaoClient {
    public static final String AUTHORIZATION = "Authorization";
    public static final String ID_CANDIDATO = "idCandidato";
    public static final String ID_INSCRICAO = "idInscricao";
    public static final String INSCRICAO_CADASTRO = "/inscricao/cadastro";
    public static final String INSCRICAO_ID_INSCRICAO = "/inscricao/{idInscricao}";
    public static final String INSCRICAO = "/inscricao";
    public static final String INSCRICAO_FIND_BY_ID_INSCRICAO = "/inscricao/find-by-idInscricao";

    public Response cadastrarInscricao(Integer idCandidato) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(InscricaoSpecs.authReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(ID_CANDIDATO, idCandidato)
                .when()
                        .post(INSCRICAO_CADASTRO);
    }

    public Response cadastrarInscricaoSemAutenticacao(Integer idCandidato) {

        return
                given()
                        .spec(InscricaoSpecs.authReqSpec())
                        .queryParam(ID_CANDIDATO, idCandidato)
                .when()
                        .post(INSCRICAO_CADASTRO)
                ;
    }

    public Response deletarInscricao(Integer idInscricao) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(InscricaoSpecs.authReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_INSCRICAO, idInscricao)
                .when()
                        .delete(INSCRICAO_ID_INSCRICAO)
                ;
    }

    public Response deletarInscricaoSemAutenticacao(Integer idInscricao) {

        return
                given()
                        .spec(InscricaoSpecs.authReqSpec())
                        .pathParam(ID_INSCRICAO, idInscricao)
                .when()
                        .delete(INSCRICAO_ID_INSCRICAO)
                ;
    }

    public Response listaUltimaInscricao() {
        Auth.obterTokenComoAdmin();

        Integer pagina = 0;
        Integer tamanho = 1;
        String sort = "idInscricao";
        Integer order = 1;

        return
                given()
                        .spec(InscricaoSpecs.authReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("sort", sort)
                        .queryParam("order", order)
                .when()
                        .get(INSCRICAO)
                ;
    }

    public Response listarInscricaoPorId(Integer idInscricao) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(InscricaoSpecs.authReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("id", idInscricao)
                .when()
                        .get(INSCRICAO_FIND_BY_ID_INSCRICAO)
                ;
    }

    public Response listarInscricaoPorIdSemAutenticacao(Integer idInscricao) {

        return
                given()
                        .spec(InscricaoSpecs.authReqSpec())
                        .queryParam("id", idInscricao)
                .when()
                        .get(INSCRICAO_FIND_BY_ID_INSCRICAO);
    }
}
