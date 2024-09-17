package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListarUsuarioTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();
    private String token;


    @BeforeEach
    public void setUp(){
        token = AuthClient.logar(Auth.usuarioGestaoDePessoas());
        System.out.println("token:" + token);
    }

    @Test
    @DisplayName("Cenário 1: listar todos os gestores")
    public void testDeveListarTodosGestores(){
        usuarioClient.listarGestores(token)
                .then()
                    .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Cenário 2: listar gestor por id")
    public void testDeveListarGestorPorId(){
        Response response = usuarioClient.listarGestorPorId(token, "1");
        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals("1", response.path("idGestor").toString()),
                () -> assertEquals("ADMIN", response.path("nome")),
                () -> assertEquals("T", response.path("ativo")));
    }
}
