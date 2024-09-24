package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listar entrevistas do gestor")
public class ListarEntrevistasGestorTest {

	private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

	@Test
	@DisplayName("Cenário 1: Validação de contrato de listar entrevistas do gestor")
	@Tag("regression")
	public void testValidarContratoListarEntrevistasGestor() {

		entrevistaClient.listarEntrevistasGestor()
				.then()
					.body(matchesJsonSchemaInClasspath("schemas/entrevista/listar_entrevistas_gestor.json"));
	}

	@Test
	@DisplayName("Cenário 2: Deve retorna 200 quando a lista de entrevistas do gestor aparecer com sucesso")
	@Tag("regression")
	public void testListarEntrevistasGestorComSucesso() {

		entrevistaClient.listarEntrevistasGestor()
				.then()
					.statusCode(HttpStatus.SC_OK);
	}

	@Test
	@DisplayName("Cenário 3: Deve retornar 403 quando tentar listar com token inválido")
	@Tag("regression")
	public void testListarEntrevistasGestorComTokenInvalido() {

		entrevistaClient.listarEntrevistasGestorComTokenInvalido()
				.then()
					.statusCode(HttpStatus.SC_FORBIDDEN);
	}

}
