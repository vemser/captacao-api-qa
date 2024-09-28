package br.com.dbccompany.vemser.tests.usuario;

import client.UsuarioClient;
import models.usuario.UsuarioModel;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Endpoint de atualizar status usuário")
public class AtualizarStatusUsuarioTest {
    private final UsuarioClient usuarioClient = new UsuarioClient();
    private final Faker faker = new Faker();
    @Test
    @DisplayName("Cenário 1: Deve atualizar status para desativo do gestor por id")
    @Tag("Regression")
    public void testDeveAtualizarStatusDesativoGestorPorId(){
        UsuarioModel usuarioAtualizado = usuarioClient.desativarContaGestor("3", true)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(UsuarioModel.class);
        assertAll(
                () -> assertEquals(3, usuarioAtualizado.getIdGestor()),
                () -> assertEquals("F", usuarioAtualizado.getAtivo()),
                () -> assertNotNull(usuarioAtualizado.getEmail()),
                () -> assertNotNull(usuarioAtualizado.getCargosDto())
        );
    }

    @Test
    @DisplayName("Cenário 2: Tentar atualizar status para desativo do gestor com id nulo")
    @Tag("Regression")
    public void testTentarAtualizarStatusDesativoGestorPorIdNulo(){
        usuarioClient.desativarContaGestor(StringUtils.EMPTY, true)
                .then()
                    .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @DisplayName("Cenário 3: Tentar atualizar status para desativo do gestor com id negativo")
    @Tag("Regression")
    public void testTentarAtualizarStatusDesativoGestorPorIdNegativo(){
        usuarioClient.desativarContaGestor(String.valueOf(faker.number().numberBetween(-100, -1)), true)
                .then()
                    .body("message", equalTo("Usuario não encontrado!"))
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 4: Tentar atualizar status para desativo do gestor sem token")
    @Tag("Regression")
    public void testTentarAtualizarStatusDesativoGestorSemToken(){
        usuarioClient.desativarContaGestor("3", false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 5: Validar schema desativar gestor")
    @Tag("Contract")
    public void testValidarSchemaDesativarGestor(){
        usuarioClient.desativarContaGestor( "3", true)
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/usuario/Desativar_conta_gestor.json"))
                    .statusCode(HttpStatus.SC_OK);
    }
}
