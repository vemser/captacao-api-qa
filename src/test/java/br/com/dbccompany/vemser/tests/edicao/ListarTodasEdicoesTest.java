package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint que lista todas as edições")
public class ListarTodasEdicoesTest {

	private static EdicaoClient edicaoClient = new EdicaoClient();

//	@Test
//	@DisplayName("Cenário 1: Deve validar o contrato de listar todas as edições")
//	@Tag("Contract")
//	public void testValidarContratoListarTodasEdicoes() {
//
//		edicaoClient.listarTodasAsEdicoes()
//				.then()
//				.statusCode(HttpStatus.SC_OK)
//				.body(matchesJsonSchemaInClasspath("schemas/edicao/listarTodasEdicoes.json"));
//	}
//
//	@Test
//	@DisplayName("Cenário 2: Deve retornar 200 ao listar todas as edições com sucesso")
//	public void testListarTodasEdicoesComSucesso() {
//
//		edicaoClient.listarTodasAsEdicoes()
//				.then()
//				.header("Content-Type", "application/json")
//				.statusCode(HttpStatus.SC_OK)
//				.body("size()", greaterThan(0));
//	}

	@Test
	@DisplayName("Cenário 3: Deve retornar 403 ao listar todas as edições sem autenticação")
	@Tag("Regression")
	public void testListarTodasEdicoesSemAutenticacao() {

		edicaoClient.listarTodasAsEdicoesSemAutenticacao()
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}

}
