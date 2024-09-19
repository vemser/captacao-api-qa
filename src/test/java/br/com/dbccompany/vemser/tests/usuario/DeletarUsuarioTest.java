package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.auth.Auth;
import static org.hamcrest.Matchers.equalTo;

public class DeletarUsuarioTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @BeforeEach
    public void setUp(){
        Auth.usuarioGestaoDePessoas();
    }

    @Test
    @DisplayName("Cenário 1: tentar deletar gestor com id nulo")
    @Tag("Regression")
    public void testTentarDeletarGestorComIdNulo(){
        usuarioClient.deletarGestor(AuthClient.getToken(), StringUtils.EMPTY)
                .then()
                    .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @DisplayName("Cenário 2: tentar deletar gestor com id negativo")
    public void testTentarDeletarGestorComIdNegativo(){
        usuarioClient.deletarGestor(AuthClient.getToken(), "-1")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("Usuario não encontrado!"));
    }

    @Test
    @DisplayName("Cenário 3: tentar deletar gestor com token inválido")
    @Tag("Regression")
    public void testTentarDeletarGestorComTokenInvalido(){
        usuarioClient.deletarGestor(StringUtils.EMPTY, "1")
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
