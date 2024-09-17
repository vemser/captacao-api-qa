package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.auth.Auth;

public class ListarUsuarioTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();
    private String token;


    @BeforeEach
    public void setUp(){
        token = AuthClient.logar(Auth.usuarioGestaoDePessoas());
        System.out.println("token:" + token);
    }

    @Test
    public void testGetTodosUsuarios(){

        usuarioClient.getUsuarios(token)
                .then()
                .statusCode(200)
        ;
    }
}
