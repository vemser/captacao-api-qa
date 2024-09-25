package br.com.dbccompany.vemser.tests.entrevista;

import client.edicao.EdicaoClient;
import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import io.restassured.response.Response;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.auth.Email;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de entrevistas por email")
class ListarEntrevistaPorEmailTest {

  private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
	private static final String PATH_SCHEMA_LISTAR_ENTREVISTA_POR_EMAIL = "schemas/entrevista/listar_entrevista_por_email.json";
	private static final EdicaoClient edicaoClient = new EdicaoClient();

	@Test
	@DisplayName("Cenário 1: Validação de contrato de listar entrevistas por email")
	@Tag("Contract")
	public void testValidarContratoListarEntrevistasPorEmail() {

		String edicao = edicaoClient.listaEdicaoAtualAutenticacao()
				.then()
				.extract().asString();

		Response response = entrevistaClient.listarTodasAsEntrevistas(edicao);
		String emailEntrevista = response.path("[0].candidatoEmail");

		entrevistaClient.listarTodasAsEntrevistasPorEmail(emailEntrevista)
				.then()
				.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_ENTREVISTA_POR_EMAIL))
		;
	}

	@Test
	@DisplayName("Cenário 2: Deve retornar 200 ao buscar entrevista por email do candidato com sucesso")
	@Tag("Regression")
	void testListaEntrevistaPorEmailComSucesso() {

		Response response = EntrevistaDataFactory.buscarTodasEntrevistas();
		String emailEntrevista = response.path("[0].candidatoEmail");

		EntrevistaCriacaoResponseModel entrevista = entrevistaClient.listarTodasAsEntrevistasPorEmail(emailEntrevista)
				.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.as(EntrevistaCriacaoResponseModel.class);

		Assertions.assertNotNull(entrevista);
		Assertions.assertEquals(emailEntrevista, entrevista.getCandidatoEmail());
	}

	@Test
  	@DisplayName("Cenário 3: Deve retornar 403 ao buscar entrevista por email do candidato sem autenticação")
	@Tag("Regression")
    void testListaEntrevistaPorEmailSemAutenticacao() {

        String emailDoCandidato = Email.getEmail();

        entrevistaClient.listarTodasAsEntrevistasPorEmailSemAutenticacao(emailDoCandidato)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
