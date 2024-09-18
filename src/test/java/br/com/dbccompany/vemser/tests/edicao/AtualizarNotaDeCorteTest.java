package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import factory.edicao.EdicaoDataFactory;
import io.restassured.response.Response;
import models.edicao.EdicaoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Endpoint de atualização de nota de corte")
public class AtualizarNotaDeCorteTest {

	private final EdicaoClient edicaoClient = new EdicaoClient();

	@Test
	@DisplayName("Cenário 2: Deve retornar 200 ao atualizar nota de corte com sucesso")
	public void testAtualizarNotaDeCorteComSucesso() {

		EdicaoModel edicao = EdicaoDataFactory.notaDeCorte();

		Response response = edicaoClient.atualizarNotaDeCorte(edicao);

		assertEquals(200, response.statusCode(), "A resposta não retornou 200 OK");

		EdicaoModel responseBody = response.getBody().as(EdicaoModel.class);
		assertEquals(edicao.getNotaCorte(), responseBody.getNotaCorte(), "A nota de corte não foi atualizada corretamente");
	}

}
