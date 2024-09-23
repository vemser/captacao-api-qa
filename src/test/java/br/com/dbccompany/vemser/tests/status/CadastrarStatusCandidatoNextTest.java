package br.com.dbccompany.vemser.tests.status;

import client.status.StatusClient;
import factory.candidato.CandidatoDataFactory;
import io.restassured.response.Response;
import models.status.StatusModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastrar status candidato next")
public class CadastrarStatusCandidatoNextTest {

	private static final StatusClient statusClient = new StatusClient();

	@BeforeEach
	void setUp() {
		Auth.usuarioGestaoDePessoas();
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 1: Validar contrato de criar status candidato next")
	public void testValidarContratoCadastrarStatusNext() {

		Response response = CandidatoDataFactory.buscarTodosCandidatos();

		Integer idCandidato = response.path("elementos[0].idCandidato");

		statusClient.cadastrarStatusNext(idCandidato)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/status/post_status_next.json"));
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 2: Validar criação de status candidato next com sucesso")
	public void testCadastrarStatusNextComSucesso() {

		Response response = CandidatoDataFactory.buscarTodosCandidatos();

		Integer idCandidato = response.path("elementos[0].idCandidato");

		StatusModel statusCriado = statusClient.cadastrarStatusNext(idCandidato)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.extract()
				.as(StatusModel.class);

		Assertions.assertNotNull(statusCriado);
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 3: Validar criação de status candidato next sem estar autenticado")
	public void testCadastrarStatusNextSemAutenticacao() {

		Response response = CandidatoDataFactory.buscarTodosCandidatos();

		Integer idCandidato = response.path("elementos[0].idCandidato");

		statusClient.cadastrarStatusNextSemAutenticacao(idCandidato)
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}
}
