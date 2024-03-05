package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import factory.edicao.EdicaoDataFactory;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("Endpoint de cadastro de edição")
class CadastrarEdicaoTest {

    private static final EdicaoClient edicaoClient = new EdicaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 ao cadastrar edição com sucesso")
    void testCadastrarEdicaoComSucesso() {
        Integer idNovaEdicao = EdicaoDataFactory.idNovaEdicao().getIdEdicao();

        EdicaoModel edicaoCadastrada = edicaoClient.criarEdicaoComNumEdicao(idNovaEdicao)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EdicaoModel.class);

        edicaoClient.deletarEdicao(edicaoCadastrada.getIdEdicao());
        Assertions.assertNotNull(edicaoCadastrada);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao cadastrar edição sem autenticação")
    void testCadastrarEdicaoSemAutenticacao() {
        edicaoClient.criarEdicaoComNumEdicaoSemAutenticacao(1)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
