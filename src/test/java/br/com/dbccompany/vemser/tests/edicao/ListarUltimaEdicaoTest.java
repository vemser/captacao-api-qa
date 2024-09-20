package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint que lista a última edição")
class ListarUltimaEdicaoTest {

    private static EdicaoClient edicaoClient = new EdicaoClient();
	private static final String PATH_SCHEMA_LISTAR_EDICOES = "schemas/edicao/listar_edicoes.json";

	@Test
	@DisplayName("Cenário 1: Validação de contrato de listar edicoes")
	@Tag("Contract")
	public void testValidarContratoListarEdicoes() {

		edicaoClient.listarTodasAsEdicoes()
				.then()
				.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_EDICOES))
		;
	}

	@Test
	@DisplayName("Cenário 2: Deve retornar 200 ao listar a última edição com sucesso")
	void testListarUltimaEdicaoComSucesso() {

		edicaoClient.listaEdicaoAtualAutenticacao()
				.then()
						.statusCode(HttpStatus.SC_OK);
	}

	@Test
    @DisplayName("Cenário 3: Deve retornar 403 ao listar a última edição sem autenticação")
  	@Tag("Regression")
    void testListarUltimaEdicaoSemAutenticacao() {

        String ultimaEdicao = edicaoClient.listaEdicaoAtualSemAutenticacao();

        Assertions.assertNotNull(ultimaEdicao);
    }
}
