package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.auth.Auth;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class AtualizarStatusUsuarioTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @BeforeEach
    public void setUp(){
        Auth.usuarioGestaoDePessoas();
    }
    @Test
    @DisplayName("Cenário 1: Deve atualizar status para desativo do gestor por id")
    @Tag("Regression")
    public void testDeveAtualizarStatusDesativoGestorPorId(){
        Response response = usuarioClient.desativarContaGestor(AuthClient.getToken(), "3");

        assertAll(
                () -> assertEquals("3", response.path("idGestor").toString()),
                () -> assertEquals("F", response.path("ativo").toString()),
                () -> assertNotNull(response.path("email"))
        );
    }

    @Test
    @DisplayName("Cenário 2: Tentar atualizar status para desativo do gestor com id nulo")
    @Tag("Regression")
    public void testTentarAtualizarStatusDesativoGestorPorIdNulo(){
        usuarioClient.deletarGestor(AuthClient.getToken(), StringUtils.EMPTY)
                .then()
                    .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @DisplayName("Cenário 3: Tentar atualizar status para desativo do gestor com id negativo")
    public void testTentarAtualizarStatusDesativoGestorPorIdNegativo(){
        usuarioClient.deletarGestor(AuthClient.getToken(), "-1")
                .then()
                    .body("message", equalTo("Usuario não encontrado!"))
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 4: Tentar atualizar status para desativo do gestor com token inválido")
    @Tag("Regression")
    public void testTentarAtualizarStatusDesativoGestorTokenInvalido(){
        usuarioClient.deletarGestor(StringUtils.EMPTY, "3")
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 5: Validar schema desativar gestor")
    public void testValidarSchemaDesativarGestor(){
        usuarioClient.desativarContaGestor(AuthClient.getToken(), "3")
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/usuario/Desativar_conta_gestor.json"))
                    .statusCode(HttpStatus.SC_OK);
    }
}
