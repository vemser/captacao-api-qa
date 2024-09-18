package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.entrevista.EntrevistaClient;
import client.linguagem.LinguagemClient;
import factory.entrevista.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.auth.Email;

@DisplayName("Endpoint de listagem de entrevistas por email")
class ListarEntrevistaPorEmailTest {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

	@Test
	@DisplayName("Cenário 1: Deve retornar 200 ao buscar entrevista por email do candidato com sucesso")
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

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao buscar entrevista por email do candidato sem autenticação")
	@Tag("Regression")
    void testListaEntrevistaPorEmailSemAutenticacao() {
        String emailDoCandidato = Email.getEmail();

        var response = entrevistaClient.listarTodasAsEntrevistasPorEmailSemAutenticacao(emailDoCandidato)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
