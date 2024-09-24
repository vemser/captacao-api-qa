package br.com.dbccompany.vemser.tests.usuario;

import client.usuario.UsuarioClient;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Endpoint de deletar usuário")
public class DeletarUsuarioTest {
    private final UsuarioClient usuarioClient = new UsuarioClient();
    private final Faker faker = new Faker();
    @Test
    @DisplayName("Cenário 1: tentar deletar gestor com id nulo")
    public void testTentarDeletarGestorComIdNulo(){
        usuarioClient.deletarGestor(StringUtils.EMPTY, true)
                .then()
                    .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @DisplayName("Cenário 2: tentar deletar gestor com id negativo")
    public void testTentarDeletarGestorComIdNegativo(){
        usuarioClient.deletarGestor(String.valueOf(faker.number().numberBetween(-100, -1)), true)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("Usuario não encontrado!"));
    }

    @Test
    @DisplayName("Cenário 3: tentar deletar gestor sem token")
    public void testTentarDeletarGestorSemToken(){
        usuarioClient.deletarGestor("1", false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
