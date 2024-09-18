package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import factory.edicao.EdicaoDataFactory;
import io.restassured.response.Response;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Endpoint de atualização de nota de corte")
public class AtualizarNotaDeCorteTest {

	private final EdicaoClient edicaoClient = new EdicaoClient();

	@Test
	@DisplayName("Cenário 1: Deve validar o contrato de atualizar nota de corte")
	public void testContratoAtualizarNotaDeCorte() {

		EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

		edicaoClient.atualizarNotaDeCorte(edicao)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/edicao/atualizarNotaDeCorte.json"));
	}

	@Test
	@DisplayName("Cenário 2: Deve retornar 200 ao atualizar nota de corte com sucesso")
	public void testAtualizarNotaDeCorteComSucesso() {

		EdicaoModel edicao = EdicaoDataFactory.notaDeCorte();

		Response response = edicaoClient.atualizarNotaDeCorte(edicao);

		assertEquals(200, response.statusCode(), "A resposta não retornou 200 OK");

		EdicaoModel responseBody = response.getBody().as(EdicaoModel.class);
		assertEquals(edicao.getNotaCorte(), responseBody.getNotaCorte(), "A nota de corte não foi atualizada corretamente");
	}

	@Test
	@DisplayName("Cenário 3: Deve retornar 200 ao atualizar nota de corte com sucesso")
	public void testAtualizarNotaDeCorteSemAutenticacao() {

		EdicaoModel edicao = EdicaoDataFactory.notaDeCorte();

		edicaoClient.atualizarNotaDeCorteSemAutenticacao(edicao)
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}

	@Test
	@DisplayName("Cenário 4: Deve retornar 400 ao atualizar nota de corte com valor inválido")
	public void testAtualizarNotaDeCorteInvalida() {

		EdicaoModel edicao = EdicaoDataFactory.notaDeCorteInvalida();

		edicaoClient.atualizarNotaDeCorte(edicao)
				.then()
				.header("Content-Type", "application/json")
				.statusCode(400)
				.body("errors[0]", equalTo("notaCorte: A nota de corte deve ser no máximo 100"));
	}

}
