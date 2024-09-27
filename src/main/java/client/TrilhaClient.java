package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.trilha.TrilhaModel;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class TrilhaClient extends BaseClient {
    private static final String AUTHORIZATION = "Authorization";
    private static final String CADASTRAR_TRILHA = "/trilha";
    private static final String LISTAR_TRILHAS = "/trilha/listar";
    private static final String DELETAR_TRILHA = "/trilha/deletar/{idTrilha}";
    private static final String ID_TRILHA = "idTrilha";

    public Response listarTodasAsTrilhas() {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .spec(super.setUp())
                .when()
                        .get(LISTAR_TRILHAS)
                ;
    }

    public Response cadastrarTrilha(TrilhaModel trilha) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .spec(super.setUp())
                        .contentType(ContentType.JSON)
                        .body(trilha)
                .when()
                        .post(CADASTRAR_TRILHA)
                ;
    }

    public Response deletarTrilhaPorId(Integer idTrilha) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .spec(super.setUp())
                        .pathParam(ID_TRILHA, idTrilha)
                .when()
                        .delete(DELETAR_TRILHA)
                ;
    }
}

