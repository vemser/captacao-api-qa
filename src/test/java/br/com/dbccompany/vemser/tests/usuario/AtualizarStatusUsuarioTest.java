package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.auth.Auth;

import static org.junit.jupiter.api.Assertions.*;

public class AtualizarStatusUsuarioTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();
    private String token;
    @BeforeEach
    public void setUp(){
        token = AuthClient.logar(Auth.usuarioGestaoDePessoas());
    }
    @Test
    @DisplayName("Cenário 1: Deve atualizar status para desativo do gestor por id")
    public void testDeveDesativarGestorPorId(){
        Response response = usuarioClient.desativarContaGestor(token, "3");

        assertAll(
                () -> assertEquals("3", response.path("idGestor").toString()),
                () -> assertEquals("F", response.path("ativo").toString()),
                () -> assertNotNull(response.path("email"))
        );
    }
}
