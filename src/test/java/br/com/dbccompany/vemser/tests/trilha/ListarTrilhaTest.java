package br.com.dbccompany.vemser.tests.trilha;

import client.trilha.TrilhaClient;
import models.trilha.TrilhaModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listar trilhas")
public class ListarTrilhaTest {

	TrilhaClient trilhaClient = new TrilhaClient();

	private static final String PATH_SCHEMA_LISTAR_TRILHAS = "schemas/trilha/listar_trilhas.json";

	@Test
	@DisplayName("Cenário 1: Validação de contrato de listar trilhas")
	@Tag("Contract")
	public void testValidarContratoListarTrilhas() {

		trilhaClient.listarTodasAsTrilhas()
				.then()
				.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_TRILHAS))
		;
	}

	@Test
	@DisplayName("Cenário 2: Deve retornar 200 quando lista as trilhas com sucesso")
	@Tag("Regression")
	void testListarTrilhasComSucesso(){

		List<TrilhaModel> trilhas = trilhaClient.listarTodasAsTrilhas()
				.then()
				.statusCode(200)
				.extract().jsonPath().getList(".", TrilhaModel.class);

		Assertions.assertNotNull(trilhas);
		Assertions.assertFalse(trilhas.isEmpty());
	}

}
