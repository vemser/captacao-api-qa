package br.com.dbccompany.vemser.tests.disponibilidade;

import client.DisponibilidadeClient;
import factory.DisponibilidadeDataFactory;
import models.disponibilidade.DisponibilidadeModel;
import models.disponibilidade.DisponibilidadeResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listar disponibilidades")
public class ListarDisponibilidadesTest {

	private static final DisponibilidadeClient disponibilidadeClient = new DisponibilidadeClient();
	private static DisponibilidadeResponseModel[] disponibilidadeCadastrada;
	private static Integer idGestor = 2;

	@BeforeEach
	public void criarMassaDados(){
		DisponibilidadeModel disponibilidadeParaCadastrar = DisponibilidadeDataFactory.disponibilidadeValida(idGestor);
		disponibilidadeCadastrada = disponibilidadeClient.cadastrarDisponibilidade(disponibilidadeParaCadastrar)
				.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
					.as(DisponibilidadeResponseModel[].class);
	}

	@AfterAll
	public static void deletarMassaDados(){
		disponibilidadeClient.deletarDisponibilidade(disponibilidadeCadastrada[0].getIdDisponibilidade())
				.then()
					.statusCode(HttpStatus.SC_NO_CONTENT);
	}

	@Test
	@Tag("Contract")
	@DisplayName("Cenário 1: Validar contrato listar disponibilidade com sucesso")
	public void testValidarContratoListarDisponibilidade() {

		disponibilidadeClient.listarDisponibilidades()
				.then()
					.body(matchesJsonSchemaInClasspath("schemas/disponibilidade/listarDisponibilidade.json"));
	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 2: Validar listar disponibilidade com sucesso")
	public void testListarDisponibilidadeComSucesso() {
		List<DisponibilidadeResponseModel> listaDisponibilidade = disponibilidadeClient.listarDisponibilidades()
				.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.body()
					.jsonPath()
					.getList("$", DisponibilidadeResponseModel.class);

		Assertions.assertNotNull(listaDisponibilidade, "A lista de disponibilidade não deve ser nula");
		Assertions.assertFalse(listaDisponibilidade.isEmpty(), "A lista de disponibilidade não deve estar vazia");

	}

	@Test
	@DisplayName("Cenário 3: Deve listar toda disponibilidade por data com sucesso")
	@Tag("Functional")
	public void testDeveListarTodaDisponibilidadePorDataComSucesso(){
		disponibilidadeClient.listarPorData(disponibilidadeCadastrada[0].getDataEntrevista())
				.then()
					.statusCode(HttpStatus.SC_OK);

	}

	@Test
	@Tag("Regression")
	@DisplayName("Cenário 4: Tentar listar disponibilidade sem estar autenticado")
	public void testListarDisponibilidadeSemAutenticacao() {
		disponibilidadeClient.listarDisponibilidadesSemAutenticacao()
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}

	@Test
	@DisplayName("Cenário 5: Tentar listar toda disponibilidade por data sem token")
	@Tag("Regression")
	public void testTentarListarTodaDispoonibilidadePorDataSemToken(){
		disponibilidadeClient.listarPorDataSemAutenticacao(disponibilidadeCadastrada[0].getDataEntrevista())
				.then()
					.statusCode(HttpStatus.SC_FORBIDDEN);
	}
}
