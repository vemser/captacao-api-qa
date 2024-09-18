package client.usuario;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import specs.usuario.UsuarioSpecs;
import static io.restassured.RestAssured.*;

public class UsuarioClient extends UsuarioSpecs {

    private static final String USUARIO = "/usuario";
    private static final String ID_GESTOR = "/id-gestor";
    private static final String EMAIL_GESTOR = "/gestor-by-nome-email";
    private static final String CONTAS_INATIVAS = "/contas-inativas";
    private static final String UPLOAD_IMAGEM = "/upload-imagem";
    private static final String DESATIVAR_CONTA = "/desativacao-conta";
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

    public Response listarTodaContaInativaGestor(String token){
        return
                given()
                        .spec(super.usuarioSetUp())
                        .contentType(ContentType.JSON)
                        .header(AUTHORIZATION, token)
                .when()
                        .get(USUARIO + CONTAS_INATIVAS);
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
}