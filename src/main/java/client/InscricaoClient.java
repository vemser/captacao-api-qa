package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class InscricaoClient extends BaseClient {

    public static final String AUTHORIZATION = "Authorization";
    public static final String ID_CANDIDATO = "idCandidato";
    public static final String ID_INSCRICAO = "idInscricao";
    public static final String INSCRICAO_CADASTRO = "/inscricao/cadastro";
    public static final String INSCRICAO_ID_INSCRICAO = "/inscricao/{idInscricao}";
    public static final String INSCRICAO = "/inscricao";
    public static final String INSCRICAO_FIND_BY_ID_INSCRICAO = "/inscricao/find-by-idInscricao";
    public static final String INSCRICAO_FILTRAR = "inscricao/filtro-inscricao";

    public Response cadastrarInscricao(Integer idCandidato) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(ID_CANDIDATO, idCandidato)
                .when()
                        .post(INSCRICAO_CADASTRO);
    }

    public Response cadastrarInscricaoSemAutenticacao(Integer idCandidato) {
        Auth.usuarioAluno();
        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(ID_CANDIDATO, idCandidato)
                .when()
                        .post(INSCRICAO_CADASTRO)
                ;
    }

    public Response deletarInscricao(Integer idInscricao) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_INSCRICAO, idInscricao)
                .when()
                        .delete(INSCRICAO_ID_INSCRICAO)
                ;
    }

    public Response listaUltimaInscricao() {
        Auth.usuarioGestaoDePessoas();

        Integer pagina = 0;
        Integer tamanho = 1;
        String sort = "idInscricao";
        Integer order = 1;

        return
                given()
                        .spec(setUp())
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
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("id", idInscricao)
                .when()
                        .get(INSCRICAO_FIND_BY_ID_INSCRICAO)
                ;
    }

    public Response listarInscricaoPorIdSemAutenticacao(Integer idInscricao) {
        Auth.usuarioAluno();
        return
                given()
                        .spec(setUp())
                        .queryParam("id", idInscricao)
                .when()
                        .get(INSCRICAO_FIND_BY_ID_INSCRICAO);
    }

    public Response filtrarInscricao(String pagina, String tamanho, String email, String edicao, String trilha, boolean isCondicaoTokenValido){
        String token = inserirToken(isCondicaoTokenValido);
        Response response =
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, token)
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("email", email)
                        .queryParam("edicao", edicao)
                        .queryParam("trilha", trilha)
                .when()
                        .get(INSCRICAO_FILTRAR);
        return response;
    }

    private String inserirToken(boolean isCondicaoInserirTokenValido){
        String token = StringUtils.EMPTY;
        if(isCondicaoInserirTokenValido){
            Auth.usuarioGestaoDePessoas();
            token = AuthClient.getToken();
        }
        return token;
    }
}
