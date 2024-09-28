package br.com.dbccompany.vemser.tests.entrevista;

import client.EntrevistaClient;
import factory.EntrevistaDataFactory;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de entrevistas por email")
class ListarEntrevistaPorEmailTest {

  	private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
	private static final String PATH_SCHEMA_LISTAR_ENTREVISTA_POR_EMAIL = "schemas/entrevista/listar_entrevista_por_email.json";
	private String emailCandidato;

	@BeforeEach
	public void setUp() {
		emailCandidato = EntrevistaDataFactory.getEmailCandidato();
	}

	@Test
	@DisplayName("Cenário 1: Validação de contrato de listar entrevistas por email")
	@Tag("Contract")
	public void testValidarContratoListarEntrevistasPorEmail() {

		entrevistaClient.listarTodasAsEntrevistasPorEmail(emailCandidato)
				.then()
				.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_ENTREVISTA_POR_EMAIL));
	}

	@Test
	@DisplayName("Cenário 2: Deve retornar 200 ao buscar entrevista por email do candidato com sucesso")
	@Tag("Regression")
	void testListaEntrevistaPorEmailComSucesso() {

		EntrevistaCriacaoResponseModel entrevista = entrevistaClient.listarTodasAsEntrevistasPorEmail(emailCandidato)
				.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.as(EntrevistaCriacaoResponseModel.class);

		Assertions.assertNotNull(entrevista);
		Assertions.assertEquals(emailCandidato, entrevista.getCandidatoEmail());
	}

	@Test
  	@DisplayName("Cenário 3: Deve retornar 403 ao buscar entrevista por email do candidato sem autenticação")
	@Tag("Regression")
    void testListaEntrevistaPorEmailSemAutenticacao() {

        entrevistaClient.listarTodasAsEntrevistasPorEmailSemAutenticacao(emailCandidato)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
