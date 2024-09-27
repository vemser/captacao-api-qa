package br.com.dbccompany.vemser.tests.usuario;

import client.usuario.UsuarioClient;
import models.usuario.UsuarioListarResponseModel;
import models.usuario.UsuarioModel;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Endpoint de listar usuário")
public class ListarUsuarioTest {
    private final UsuarioClient usuarioClient = new UsuarioClient();
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Cenário 1: Deve listar todos os gestores")
    @Tag("Regression")
    public void testDeveListarTodosGestores(){
        UsuarioListarResponseModel listarTodoUsuario = usuarioClient.listarGestores(true)
                .then()
                    .statusCode(200)
                    .extract()
                    .as(UsuarioListarResponseModel.class);
        assertAll(
                () -> assertNotNull(listarTodoUsuario.getElementos()),
                () -> assertNotNull(listarTodoUsuario.getTotalElementos())
        );
    }

    @Test
    @DisplayName("Cenário 2: Deve listar gestor por id")
    @Tag("Regression")
    public void testDeveListarGestorPorId(){
        UsuarioModel usuarioEncontrado = usuarioClient.listarGestorPorId( "1", true)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(UsuarioModel.class);
        assertAll(
                () -> assertEquals(1, usuarioEncontrado.getIdGestor()),
                () -> assertEquals("ADMIN", usuarioEncontrado.getNome()),
                () -> assertEquals("T", usuarioEncontrado.getAtivo()),
                () -> assertNotNull(usuarioEncontrado.getEmail())
        );
    }

    @Test
    @DisplayName("Cenário 3: Deve listar todas as contas inativas")
    @Tag("Regression")
    public void testDeveListarTodaContaInativa(){
        usuarioClient.desativarContaGestor("3", true);
        List<UsuarioModel> listaTodoUsuarioInativo = usuarioClient.listarTodoGestorInativo(true)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .jsonPath()
                    .getList("", UsuarioModel.class);
        assertNotNull(listaTodoUsuarioInativo.get(0));
    }

    @Test
    @DisplayName("Cenário 4: Deve listar os dados do usuário logado")
    @Tag("Functional")
    public void testDeveListarDadosUsuarioLogado(){
        UsuarioModel usuarioLogado = usuarioClient.listarDadosMe(true)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(UsuarioModel.class);
        assertAll(
                () -> assertNotNull(usuarioLogado),
                () -> assertNotNull(usuarioLogado.getCargosDto()),
                () -> assertNotNull(usuarioLogado.getEmail()),
                () -> assertNotNull(usuarioLogado.getIdGestor())
        );
    }

    @Test
    @DisplayName("Cenrário 5: Tentar listar gestor com id nulo")
    @Tag("Regression")
    public void testTentarListarGestorComIdNulo(){
        usuarioClient.listarGestorPorId(StringUtils.EMPTY, true)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 6: Tentar listar gestor com id negativo")
    @Tag("Regression")
    public void testTentarListarGestorComIdNegativo(){
        usuarioClient.listarGestorPorId( String.valueOf(faker.number().numberBetween(-100, -1)), true)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("Usuario não encontrado!"));
    }

    @Test
    @DisplayName("Cenário 7: Tentar listar todo gestor com token inválido")
    @Tag("Regression")
    public void testTentarListarTodoGestorComTokenInvalido(){
        usuarioClient.listarGestores(false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 8: Tentar listar gestor por id com token inválido")
    @Tag("Regression")
    public void testTentarListarGestorPorIdComTokenInvalido(){
        usuarioClient.listarGestorPorId( "1", false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 9: Tentar listar contas inativas com token inválido")
    @Tag("Regression")
    public void testTentarListarContaInativaComTokenInvalido(){
        usuarioClient.listarTodoGestorInativo(false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 10: Validar schema listar todo gestor")
    @Tag("Contract")
    public void testValidarSchemaListarTodoGestor(){
        usuarioClient.listarGestores(true)
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/usuario/Listar_todo_gestor.json"))
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Cenário 11: Validar schema listar gestor por id")
    @Tag("Contract")
    public void testValidarSchemaListarGestorPorId(){
        usuarioClient.listarGestorPorId( "1", true)
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/usuario/Listar_gestor_por_id.json"))
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Cenário 12: Validar schema listar todo gestor inativo")
    @Tag("Contract")
    public void testValidarSchemaListarTodoGestorInativo(){
        usuarioClient.listarTodoGestorInativo(true)
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/usuario/Listar_todo_gestor_inativo.json"))
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Cenário 13: Tentar listar os dados do usuário logado sem token")
    @Tag("Regression")
    public void testTentarListarDadosUsuarioLogadoSemToken(){
        usuarioClient.listarDadosMe(false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
