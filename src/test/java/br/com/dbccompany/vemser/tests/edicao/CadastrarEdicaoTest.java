package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import factory.edicao.EdicaoDataFactory;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastro de edição")
class CadastrarEdicaoTest {


	private static final EdicaoClient edicaoClient = new EdicaoClient();

	@Test
	@DisplayName("Cenário 1: Deve validar o contrato de cadastrar edição")
	@Tag("Regression")
	public void testValidarContraroCadastrarEdicao() {

		EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

		edicaoClient.cadastrarEdicao(edicao)
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.body(matchesJsonSchemaInClasspath("schemas/edicao/cadastrarEdicao.json"));
	}

	@Test
	@DisplayName("Cenário 2: Deve retornar 201 ao cadastrar edição com sucesso")
	@Tag("Regression")
	void testCadastrarEdicaoComSucesso() {

		EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

		EdicaoModel edicaoCadastrada = edicaoClient.cadastrarEdicao(edicao)
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.extract()
				.as(EdicaoModel.class);

		edicaoClient.deletarEdicao(edicaoCadastrada.getIdEdicao());
		Assertions.assertNotNull(edicaoCadastrada);
	}

	@Test
	@DisplayName("Cenário 3: Deve retornar 403 ao cadastrar edição sem autenticação")
	@Tag("Regression")
	void testCadastrarEdicaoSemAutenticacao() {
		edicaoClient.criarEdicaoComNumEdicaoSemAutenticacao(1)
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}
}
