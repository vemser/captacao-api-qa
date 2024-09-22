package br.com.dbccompany.vemser.tests.status;

import client.status.StatusClient;
import factory.candidato.CandidatoDataFactory;
import io.restassured.response.Response;
import models.status.StatusModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastrar status candidato previus")
public class CadastrarStatusCandidatoPreviusTest {

	private static final StatusClient statusClient = new StatusClient();

	@BeforeEach
	void setUp() {
		Auth.usuarioGestaoDePessoas();
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 1: Validar contrato de criar status candidato previus")
	public void testValidarContratoCadastrarStatusPrevius() {

		Response response = CandidatoDataFactory.buscarTodosCandidatos();

		Integer idCandidato = response.path("elementos[0].idCandidato");

		statusClient.cadastrarStatusPrevius(idCandidato)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/status/post_status_previus.json"));
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 2: Validar criação de status candidato previus com sucesso")
	public void testCadastrarStatusPreviusComSucesso() {

		Response response = CandidatoDataFactory.buscarTodosCandidatos();

		Integer idCandidato = response.path("elementos[0].idCandidato");

		StatusModel statusCriado = statusClient.cadastrarStatusPrevius(idCandidato)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.extract()
				.as(StatusModel.class);

		Assertions.assertNotNull(statusCriado);
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 3: Validar criação de status candidato previus sem estar autenticado")
	public void testCadastrarStatusPreviusSemAutenticacao() {

		Response response = CandidatoDataFactory.buscarTodosCandidatos();

		Integer idCandidato = response.path("elementos[0].idCandidato");

		statusClient.cadastrarStatusPreviusSemAutenticacao(idCandidato)
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}

}
