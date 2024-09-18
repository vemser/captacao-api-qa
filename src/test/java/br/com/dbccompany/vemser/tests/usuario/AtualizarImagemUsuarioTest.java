package br.com.dbccompany.vemser.tests.usuario;

import client.auth.AuthClient;
import client.usuario.UsuarioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.auth.Auth;

public class AtualizarImagemUsuarioTest {
    private UsuarioClient usuarioClient = new UsuarioClient();
    private String token;

    @BeforeEach
    public void setUp(){
        token = AuthClient.logar(Auth.usuarioGestaoDePessoas());
        System.out.println(token);
    }

    @Test
    @DisplayName("Cenário 1: Deve atualizar imagem")
    public void testDeveAtualizarImagem(){
        
    }
}
