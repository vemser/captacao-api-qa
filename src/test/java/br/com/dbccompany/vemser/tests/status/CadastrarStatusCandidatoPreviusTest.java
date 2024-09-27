package br.com.dbccompany.vemser.tests.status;

import client.CandidatoClient;
import client.EdicaoClient;
import client.FormularioClient;
import client.StatusClient;
import factory.CandidatoDataFactory;
import io.restassured.response.Response;
import models.failure.JSONFailureResponseWithoutArrayModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.candidato.CandidatoResponseModel;
import models.status.StatusModel;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastrar status candidato previus")
public class CadastrarStatusCandidatoPreviusTest {

	private static final StatusClient statusClient = new StatusClient();
	private static final CandidatoClient candidatoClient = new CandidatoClient();
	private static final EdicaoClient edicaoClient = new EdicaoClient();
	private static final FormularioClient formularioClient = new FormularioClient();
	private static final Faker faker = new Faker();

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

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 4: Validar retroceder da prova técnica para pré-prova")
	public void testRetrocederEtapaPreProva() {

		CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
				.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
					.as(CandidatoCriacaoResponseModel.class);

		candidatoClient.avancarCandidatoEtapa(candidatoCadastrado.getIdCandidato())
				.then()
					.statusCode(HttpStatus.SC_OK);

		candidatoClient.avancarCandidatoEtapa(candidatoCadastrado.getIdCandidato())
				.then()
					.statusCode(HttpStatus.SC_OK);

		CandidatoResponseModel candidato = candidatoClient.retrocederEtapaCandidato(candidatoCadastrado.getIdCandidato())
						.then()
							.statusCode(HttpStatus.SC_OK)
							.extract().as(CandidatoResponseModel.class);

		Assertions.assertEquals("PRE_PROVA", candidato.getStatusCandidato());

		edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
		formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
		candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 5: Validar retroceder etapa do candidato para prova técnica com id inexistente")
	public void testRetrocederEtapaCandidatoInexistente() {

		JSONFailureResponseWithoutArrayModel response = candidatoClient.retrocederEtapaCandidato(faker.number().numberBetween(90000, 1000000))
				.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.as(JSONFailureResponseWithoutArrayModel.class);

		Assertions.assertEquals("Candidato não encontrado", response.getMessage());
	}

}
