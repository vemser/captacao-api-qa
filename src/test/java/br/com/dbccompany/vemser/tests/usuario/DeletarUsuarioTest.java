package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.auth.Auth;

import static org.hamcrest.Matchers.equalTo;

public class DeletarUsuarioTest {

    private UsuarioClient usuarioClient = new UsuarioClient();
    private String token;

    @BeforeEach
    public void setUp(){
        token = AuthClient.logar(Auth.usuarioGestaoDePessoas());
    }

    @Test
    @DisplayName("Cenário 1: tentar deletar gestor com id nulo")
    public void testTentarDeletarGestorComIdNulo(){
        usuarioClient.deletarGestor(token, StringUtils.EMPTY)
                .then()
                    .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @DisplayName("Cenário 2: tentar deletar gestor com id negativo")
    public void testTentarDeletarGestorComIdNegativo(){
        usuarioClient.deletarGestor(token, "-1")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("Usuario não encontrado!"));
    }

    @Test
    @DisplayName("Cenário 3: tentar deletar gestor com token inválido")
    public void testTentarDeletarGestorComTokenInvalido(){
        usuarioClient.deletarGestor(StringUtils.EMPTY, "1")
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
