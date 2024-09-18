package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListarUsuarioTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();
    private String token;


    @BeforeEach
    public void setUp(){
        token = AuthClient.logar(Auth.usuarioGestaoDePessoas());
        System.out.println(token);
    }

    @Test
    @DisplayName("Cenário 1: Deve listar todos os gestores")
    public void testDeveListarTodosGestores(){
        usuarioClient.listarGestores(token)
                .then()
                    .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve listar gestor por id")
    public void testDeveListarGestorPorId(){
        Response response = usuarioClient.listarGestorPorId(token, "1");
        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals("1", response.path("idGestor").toString()),
                () -> assertEquals("ADMIN", response.path("nome")),
                () -> assertEquals("T", response.path("ativo")));
    }

    @Test
    @DisplayName("Cenário 3: Deve listar todas as contas inativas")
    public void testDeveListarTodaContaInativa(){
        Response response = usuarioClient.listarTodaContaInativaGestor(token);

        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals("[F]", response.path("ativo").toString())
        );


    }

    @Test
    @DisplayName("Cenrário 4: Tentar listar gestor com id nulo")
    public void testTentarListarGestorComIdNulo(){
        usuarioClient.listarGestorPorId(token, StringUtils.EMPTY)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 5: Tentar listar gestor com id negativo")
    public void testTentarListarGestorComIdNegativo(){
        usuarioClient.listarGestorPorId(token, "-1")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("Usuario não encontrado!"));
    }
}
