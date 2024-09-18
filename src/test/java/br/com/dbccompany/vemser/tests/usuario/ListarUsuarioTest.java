package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListarUsuarioTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();
    private String token;


    @BeforeEach
    public void setUp(){
        token = AuthClient.logar(Auth.usuarioGestaoDePessoas());
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
        Response response = usuarioClient.listarTodoGestorInativo(token);

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

    @Test
    @DisplayName("Cenário 6: Tentar listar todo gestor com token inválido")
    public void testTentarListarTodoGestorComTokenInvalido(){
        usuarioClient.listarGestores(StringUtils.EMPTY)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 7: Tentar listar gestor com token inválido")
    public void testTentarListarGestorComTokenInvalido(){
        usuarioClient.listarGestorPorId(StringUtils.EMPTY, "1")
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 8: Tentar listar contas inativas com token inválido")
    public void testTentarListarContaInativaComTokenInvalido(){
        usuarioClient.listarTodoGestorInativo(StringUtils.EMPTY)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 9: Validar schema listar todo gestor")
    public void testValidarSchemaListarTodoGestor(){
        usuarioClient.listarGestores(token)
                .then()
                    .body(matchesJsonSchemaInClasspath("gestor/Listar_todo_gestor.json"))
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Cenário 10: Validar schema listar gestor por id")
    public void testValidarSchemaListarGestorPorId(){
        usuarioClient.listarGestorPorId(token, "1")
                .then()
                    .body(matchesJsonSchemaInClasspath("gestor/Listar_gestor_por_id.json"))
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Cenário 10: Validar schema listar todo gestor inativo")
    public void testValidarSchemaListarTodoGestorInativo(){
        usuarioClient.listarTodoGestorInativo(token)
                .then()
                .body(matchesJsonSchemaInClasspath("gestor/Listar_todo_gestor_inativo.json"))
                .statusCode(HttpStatus.SC_OK);
    }
}
