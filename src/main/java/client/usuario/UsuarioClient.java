package client.usuario;

import client.auth.AuthClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import specs.usuario.UsuarioSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class UsuarioClient extends UsuarioSpecs {

    private static final String USUARIO = "/usuario";
    private static final String ID_GESTOR = "/id-gestor";
    private static final String CONTAS_INATIVAS = "/contas-inativas";
    private static final String DESATIVAR_CONTA = "/desativacao-conta";
    private static final String ME = "/me";
    private static final String AUTHORIZATION = "Authorization";

    public Response listarGestores(String token){
        return
                given()
                        .spec(super.usuarioSetUp())
                        .contentType(ContentType.JSON)
                        .header(AUTHORIZATION, token)
                .when()
                        .get(USUARIO)
                ;
    }

    public Response listarGestorPorId(String token, String id){
        return
                given()
                        .spec(super.usuarioSetUp())
                        .contentType(ContentType.JSON)
                        .header(AUTHORIZATION, token)
                        .queryParam("idGestor", id)
                .when()
                        .get(USUARIO + ID_GESTOR);

    }

    public Response listarTodoGestorInativo(String token){
        return
                given()
                        .spec(super.usuarioSetUp())
                        .contentType(ContentType.JSON)
                        .header(AUTHORIZATION, token)
                .when()
                        .get(USUARIO + CONTAS_INATIVAS);
    }

    public Response listarDadosMe(boolean isCondicaoInserirToken){
        Response response =
                given()
                        .spec(super.usuarioSetUp())
                        .header(AUTHORIZATION, inserirToken(isCondicaoInserirToken))
                .when()
                        .get(USUARIO + ME);
        return response;
    }

    public Response desativarContaGestor(String token, String id){
        return given()
                    .spec(super.usuarioSetUp())
                    .contentType(ContentType.JSON)
                    .header(AUTHORIZATION, token)
                    .pathParam("idGestor", id)
                .when()
                    .put(USUARIO + DESATIVAR_CONTA + "/{idGestor}");

    }

    public Response deletarGestor(String token, String id){
        return  given()
                    .spec(super.usuarioSetUp())
                    .contentType(ContentType.JSON)
                    .header(AUTHORIZATION, token)
                    .pathParam("idGestor", id)
                .when()
                    .delete(USUARIO + "/{idGestor}");
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