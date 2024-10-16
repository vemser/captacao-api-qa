package br.com.dbccompany.vemser.tests.trilha;

import client.TrilhaClient;
import factory.TrilhaDataFactory;
import io.restassured.response.Response;
import models.trilha.TrilhaModel;
import models.trilha.TrilhaResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastrar trilhas")
public class CadastrarTrilhaTest {

	TrilhaClient trilhaClient = new TrilhaClient();
	private static final String PATH_SCHEMA_CADASTRAR_TRILHA = "schemas/trilha/post_trilha.json";
	TrilhaModel trilha;

	@BeforeEach
	void setUp() {
		trilha = TrilhaDataFactory.trilhaValida();
	}

	@Test
	@DisplayName("Cenário 1: Validação de contrato de cadastrar trilha")
	@Tag("Contract")
	public void testValidarContratoCadastrarTrilha() {

		trilhaClient.cadastrarTrilha(trilha)
				.then()
				.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_CADASTRAR_TRILHA));
	}

	@Test
	@DisplayName("Cenário 2: Deve retornar 201 quando cadastra trilha com sucesso")
	@Tag("Regression")
	void testCadastroDeTrilhaComSucesso(){

		TrilhaResponse trilhaResponse = trilhaClient.cadastrarTrilha(trilha)
				.then()
				.statusCode(200)
				.extract().as(TrilhaResponse.class);

		Integer idTrilha = Integer.parseInt(String.valueOf(trilhaResponse.getIdTrilha()));

		Response response = trilhaClient.deletarTrilhaPorId(idTrilha);

		response.then().statusCode(204);
	}

}
