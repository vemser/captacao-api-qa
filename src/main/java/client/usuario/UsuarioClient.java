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

    public Response listarGestores(String token){
        return
                given()
                        .spec(super.usuarioSetUp())
                        .contentType(ContentType.JSON)
                        .header("Authorization", token)
                .when()
                        .get(USUARIO)
                ;
    }

    public Response listarGestorPorId(String token, String id){
        return
                given()
                        .spec(super.usuarioSetUp())
                        .contentType(ContentType.JSON)
                        .header("Authorization", token)
                        .queryParam("idGestor", id)
                .when()
                        .get(USUARIO+ID_GESTOR);

    }
}