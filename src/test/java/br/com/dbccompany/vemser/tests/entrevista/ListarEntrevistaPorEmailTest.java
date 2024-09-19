package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
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

	@Test
	@DisplayName("Cenário 1: Validação de contrato de listar entrevistas por email")
	@Tag("Contract")
	public void testValidarContratoListarEntrevistasPorEmail() {

		entrevistaClient.listarTodasAsEntrevistasPorEmail("email@mail.com")
				.then()
				.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_ENTREVISTA_POR_EMAIL))
		;
	}


	@DisplayName("Cenário 2: Deve retornar 200 ao buscar entrevista por email do candidato com sucesso")
	@Tag("Regression")
	void testListaEntrevistaPorEmailComSucesso() {

		EntrevistaCriacaoResponseModel entrevista = entrevistaClient.listarTodasAsEntrevistasPorEmail("email@mail.com")
				.then()
				.statusCode(HttpStatus.SC_OK)
				.extract()
				.as(EntrevistaCriacaoResponseModel.class);

		Assertions.assertNotNull(entrevista);
		Assertions.assertEquals("email@mail.com", entrevista.getCandidatoEmail());
	}

  @DisplayName("Cenário 3: Deve retornar 403 ao buscar entrevista por email do candidato sem autenticação")
	@Tag("Regression")
    void testListaEntrevistaPorEmailSemAutenticacao() {
        String emailDoCandidato = Email.getEmail();

        var response = entrevistaClient.listarTodasAsEntrevistasPorEmailSemAutenticacao(emailDoCandidato)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
