package br.com.dbccompany.vemser.tests.disponibilidade;

import client.disponibilidade.DisponibilidadeClient;
import factory.disponibilidade.DisponibilidadeDataFactory;
import models.JSONFailureResponseWithArrayModel;
import models.JSONFailureResponseWithoutArrayModel;
import models.disponibilidade.DisponibilidadeModel;
import models.disponibilidade.DisponibilidadeResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastrar disponibilidade")
public class CadastrarDisponibilidadeTest {

	private final DisponibilidadeClient disponibilidadeClient = new DisponibilidadeClient();
	public Integer idGestor = 1;

	@BeforeEach
	void setUp() {
		Auth.usuarioGestaoDePessoas();
	}

	@Test
	@Tag("Contract")
	@DisplayName("Cenário 1: Validar contrato criar disponibilidade com sucesso")
	public void testValidarContratoCriarDisponibilidade() {
		DisponibilidadeModel disponibilidade = DisponibilidadeDataFactory.disponibilidadeValida(idGestor);

		disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
				.body(matchesJsonSchemaInClasspath("schemas/disponibilidade/cadastrarDisponibilidade.json"));
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 2: Validar criar disponibilidade com sucesso")
	public void testCriarDisponibilidadeComSucesso() {
		DisponibilidadeModel disponibilidade = DisponibilidadeDataFactory.disponibilidadeValida(idGestor);

		DisponibilidadeResponseModel[] responseArray = disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.extract()
				.as(DisponibilidadeResponseModel[].class);

		DisponibilidadeResponseModel response = responseArray[0];

		Assertions.assertNotNull(response.getIdDisponibilidade());
		Assertions.assertEquals(disponibilidade.getDataEntrevista(), response.getDataEntrevista());
		Assertions.assertEquals(disponibilidade.getHoraInicio(), response.getHoraInicio());
		Assertions.assertEquals(disponibilidade.getHoraFim(), response.getHoraFim());
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 3: Tentar criar disponibilidade com Id de gestor não existente")
	public void testTentarCriarDisponibilidadeComIdGestorNaoExistente() {
		DisponibilidadeModel disponibilidade = DisponibilidadeDataFactory.disponibilidadeIdGestorInexistente();

		JSONFailureResponseWithoutArrayModel response = disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				.extract()
				.as(JSONFailureResponseWithoutArrayModel.class);

		Assertions.assertEquals("Usuario não encontrado!", response.getMessage());
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 4: Tentar criar disponibilidade para data no passado")
	public void testTentarCriarDisponibilidadeParaDataNoPassado() {
		DisponibilidadeModel disponibilidade = DisponibilidadeDataFactory.disponibilidadeDataPassado(idGestor);

		JSONFailureResponseWithArrayModel response = disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				.extract()
				.as(JSONFailureResponseWithArrayModel.class);

		Assertions.assertEquals("dataEntrevista: Entrevista precisa ser no presente ou futuro.", response.getErrors().get(0));
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 5: Tentar criar disponibilidade para data e horário já marcado")
	public void testTentarCriarDisponibilidadeParaHorarioJaAgendado() {
		DisponibilidadeModel disponibilidade = DisponibilidadeDataFactory.disponibilidadeValida(idGestor);

		disponibilidadeClient.cadastrarDisponibilidade(disponibilidade);

		JSONFailureResponseWithoutArrayModel response = disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				.extract()
				.as(JSONFailureResponseWithoutArrayModel.class);

		Assertions.assertTrue(response.getMessage().contains("Já existe uma disponibilidade cadastrada no horário"));
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 6: Tentar criar disponibilidade para data invalida")
	public void testTentarCriarDisponibilidadeParaDataInvalida() {
		DisponibilidadeModel disponibilidade = DisponibilidadeDataFactory.disponibilidadeDataInvalida(idGestor);

		disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST);

	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 7: Tentar criar disponibilidade para horarios invalidos")
	public void testTentarCriarDisponibilidadeParaHorariosInvalidos() {
		DisponibilidadeModel disponibilidade = DisponibilidadeDataFactory.disponibilidadeHorariosInvalidos(idGestor);

		disponibilidadeClient.cadastrarDisponibilidade(disponibilidade)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}


}
