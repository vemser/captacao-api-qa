package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.avaliacao.AvaliacaoCriacaoModel;
import org.apache.commons.lang3.StringUtils;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class AvaliacaoClient extends BaseClient {
    public static final String AUTHORIZATION = "Authorization";
    private static final String AVALIACAO = "/avaliacao";
    private static final String AVALIACAO_POR_ID = "/avaliacao/{idAvaliacao}";
    private static final String UPDATE_AVALIACAO = "/avaliacao/update/{idAvaliacao}";
    private static final String ID_AVALIACAO = "idAvaliacao";

    public Response cadastrarAvaliacao(AvaliacaoCriacaoModel avaliacao, boolean isCondicaoInserirTokenValido) {
        String token = inserirToken(isCondicaoInserirTokenValido);
        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, token)
                        .queryParam("token", token)
                        .body(avaliacao)
                        .contentType(ContentType.JSON)
                .when()
                        .post(AVALIACAO);
    }

    public Response deletarAvaliacao(Integer idAvaliacao, boolean isCondicaoInserirTokenValido) {
        String token = inserirToken(isCondicaoInserirTokenValido);
        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, token)
                        .pathParam(ID_AVALIACAO, idAvaliacao)
                .when()
                        .delete(AVALIACAO_POR_ID)
                ;
    }

    public Response listarTodaAvaliacao(boolean isCondicaoInserirTokenValido) {
        String token = inserirToken(isCondicaoInserirTokenValido);
        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, token)
                        .queryParam("pagina", 0)
                        .queryParam("tamanho", 1)
                        .queryParam("sort", ID_AVALIACAO)
                        .queryParam("order", 0)
                .when()
                        .get(AVALIACAO);
    }

    public Response atualizarAvaliacao(Integer idAvaliacao, AvaliacaoCriacaoModel avaliacao, boolean isCondicaoInserirTokenValido) {
        String token = inserirToken(isCondicaoInserirTokenValido);
        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, token)
                        .pathParam(ID_AVALIACAO, idAvaliacao)
                        .contentType(ContentType.JSON)
                        .body(avaliacao)
                .when()
                        .put(UPDATE_AVALIACAO)
                ;
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
