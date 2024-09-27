package br.com.dbccompany.vemser.tests.status;

import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.status.StatusClient;
import factory.candidato.CandidatoDataFactory;
import io.restassured.response.Response;
import models.JSONFailureResponseWithoutArrayModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.candidato.CandidatoResponseModel;
import models.status.StatusModel;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastrar status candidato next")
public class CadastrarStatusCandidatoNextTest {

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
	@Tag("Contract")
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

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 4: Validar avanço de etapa do candidato para pré-prova")
	public void testAvancarEtapaPreProva() {

		CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
				.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
					.as(CandidatoCriacaoResponseModel.class);

		CandidatoResponseModel candidato = candidatoClient.avancarCandidatoEtapa(candidatoCadastrado.getIdCandidato())
				.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.as(CandidatoResponseModel.class);

		Assertions.assertEquals("PRE_PROVA", candidato.getStatusCandidato());

		edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
		formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
		candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 5: Validar avanço de etapa do candidato para prova técnica")
	public void testAvancarEtapaProvaTecnica() {

		CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
				.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
					.as(CandidatoCriacaoResponseModel.class);

		candidatoClient.avancarCandidatoEtapa(candidatoCadastrado.getIdCandidato())
				.then()
					.statusCode(HttpStatus.SC_OK);

		CandidatoResponseModel candidato = candidatoClient.avancarCandidatoEtapa(candidatoCadastrado.getIdCandidato())
				.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.as(CandidatoResponseModel.class);

		Assertions.assertEquals("PROVA_TECNICA", candidato.getStatusCandidato());

		edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
		formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
		candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 6: Validar avanço de etapa do candidato para prova técnica com id inexistente")
	public void testAvancarEtapaCandidatoInexistente() {

		JSONFailureResponseWithoutArrayModel response = candidatoClient.avancarCandidatoEtapa(faker.number().numberBetween(90000, 1000000))
				.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.as(JSONFailureResponseWithoutArrayModel.class);

		Assertions.assertEquals("Candidato não encontrado", response.getMessage());
	}

}
