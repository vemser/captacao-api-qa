package br.com.dbccompany.vemser.tests.disponibilidade;

import client.DisponibilidadeClient;
import factory.DisponibilidadeDataFactory;
import models.failure.JSONFailureResponseWithArrayModel;
import models.failure.JSONFailureResponseWithoutArrayModel;
import models.disponibilidade.DisponibilidadeModel;
import models.disponibilidade.DisponibilidadeResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastrar disponibilidade")
public class CadastrarDisponibilidadeTest {

	public static final String ERRO_DISPONIBILIDADE_JA_CADASTRADA = "Já existe uma disponibilidade cadastrada no horário";
	public static final String ERRO_USUARIO_NAO_ENCONTRADO = "Usuario não encontrado!";
	public static final String ERRO_ENTREVISTA_DATA_PASSADO = "dataEntrevista: Entrevista precisa ser no presente ou futuro.";
	private final DisponibilidadeClient disponibilidadeClient = new DisponibilidadeClient();
	private DisponibilidadeModel disponibilidade;
	public Integer ID_GESTOR = 2;

	@BeforeEach
	void setUp() {
		Auth.usuarioGestaoDePessoas();
		disponibilidade = DisponibilidadeDataFactory.disponibilidadeValida(ID_GESTOR);
	}

	@Test
	@Tag("Contract")
	@DisplayName("Cenário 1: Validar contrato criar disponibilidade com sucesso")
	public void testValidarContratoCriarDisponibilidade() {
		disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
					.body(matchesJsonSchemaInClasspath("schemas/disponibilidade/cadastrarDisponibilidade.json"));
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 2: Validar criar disponibilidade com sucesso")
	public void testCriarDisponibilidadeComSucesso() {
		DisponibilidadeResponseModel[] responseArray = disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
					.as(DisponibilidadeResponseModel[].class);

		DisponibilidadeResponseModel response = responseArray[0];

		deletarDisponibilidade(response);

		Assertions.assertNotNull(response.getIdDisponibilidade());
		Assertions.assertEquals(disponibilidade.getDataEntrevista(), response.getDataEntrevista());
		Assertions.assertEquals(disponibilidade.getHoraInicio(), response.getHoraInicio());
		Assertions.assertEquals(disponibilidade.getHoraFim(), response.getHoraFim());
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 3: Tentar criar disponibilidade com Id de gestor não existente")
	public void testTentarCriarDisponibilidadeComIdGestorNaoExistente() {

		DisponibilidadeModel disponibilidadeGestorIdInexistente =
				DisponibilidadeDataFactory.disponibilidadeIdGestorInexistente();

		JSONFailureResponseWithoutArrayModel response = disponibilidadeClient.cadastrarDisponibilidade(disponibilidadeGestorIdInexistente)
				.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.as(JSONFailureResponseWithoutArrayModel.class);

		Assertions.assertEquals(ERRO_USUARIO_NAO_ENCONTRADO, response.getMessage());

	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 4: Tentar criar disponibilidade para data no passado")
	public void testTentarCriarDisponibilidadeParaDataNoPassado() {

		DisponibilidadeModel disponibilidadeDataPassado =
				DisponibilidadeDataFactory.disponibilidadeDataPassado(ID_GESTOR);

		JSONFailureResponseWithArrayModel response = disponibilidadeClient.cadastrarDisponibilidade(disponibilidadeDataPassado)
				.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST)
					.extract()
					.as(JSONFailureResponseWithArrayModel.class);

		Assertions.assertEquals(ERRO_ENTREVISTA_DATA_PASSADO, response.getErrors().get(0));
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 5: Tentar criar disponibilidade para data e horário já marcado")
	public void testTentarCriarDisponibilidadeParaHorarioJaAgendado() {

		DisponibilidadeResponseModel[] responseArray = disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
					.as(DisponibilidadeResponseModel[].class);

		DisponibilidadeResponseModel disponibilidadeCadastrada = responseArray[0];

		JSONFailureResponseWithoutArrayModel response =
				disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
					.then()
						.statusCode(HttpStatus.SC_BAD_REQUEST)
						.extract()
						.as(JSONFailureResponseWithoutArrayModel.class);

		deletarDisponibilidade(disponibilidadeCadastrada);

		Assertions.assertTrue(response.getMessage().contains(ERRO_DISPONIBILIDADE_JA_CADASTRADA));

	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 6: Tentar criar disponibilidade para data invalida")
	public void testTentarCriarDisponibilidadeParaDataInvalida() {

		DisponibilidadeModel disponibilidadeDataInvalida =
				DisponibilidadeDataFactory.disponibilidadeDataInvalida(ID_GESTOR);

		disponibilidadeClient.cadastrarDisponibilidade(disponibilidadeDataInvalida)
				.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST);

	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 7: Tentar criar disponibilidade para horarios invalidos")
	public void testTentarCriarDisponibilidadeParaHorariosInvalidos() {

		DisponibilidadeModel disponibilidadeHorarioInvalido =
				DisponibilidadeDataFactory.disponibilidadeHorariosInvalidos(ID_GESTOR);

		disponibilidadeClient.cadastrarDisponibilidade(disponibilidadeHorarioInvalido)
				.then()
					.statusCode(HttpStatus.SC_BAD_REQUEST);

	}

	private void deletarDisponibilidade(DisponibilidadeResponseModel disponibilidadeCriada) {

		disponibilidadeClient.deletarDisponibilidade(
				disponibilidadeCriada.getIdDisponibilidade()
		);

	}
}
