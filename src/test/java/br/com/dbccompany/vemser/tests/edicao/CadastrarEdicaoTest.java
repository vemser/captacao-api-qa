package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import factory.edicao.EdicaoDataFactory;
import factory.trilha.TrilhaDataFactory;
import models.edicao.EdicaoModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastro de edição")
class CadastrarEdicaoTest {

    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final String PATH_SCHEMA_CADASTRAR_EDICAO = "schemas/edicao/post_edicao.json";

    @Test
    @DisplayName("Cenário 1: Validação de contrato de cadastrar edicao")
    public void testValidarContratoCadastrarEdicao() {

        EdicaoModel edicao = EdicaoDataFactory.idNovaEdicao();

        edicaoClient.cadastrarEdicao(edicao)
                .then()
                    .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_CADASTRAR_EDICAO))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 201 ao cadastrar edição com sucesso")
    void testCadastrarEdicaoComSucesso() {

		EdicaoModel edicao = EdicaoDataFactory.idNovaEdicao();

        EdicaoModel edicaoCadastrada = edicaoClient.cadastrarEdicao(edicao)
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
