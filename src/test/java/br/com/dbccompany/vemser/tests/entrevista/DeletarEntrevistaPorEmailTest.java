package br.com.dbccompany.vemser.tests.entrevista;

import client.EdicaoClient;
import client.EntrevistaClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de entrevista por email")
class DeletarEntrevistaPorEmailTest {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
	String edicao;
	String emailCandidato;

	@BeforeEach
	void setUp() {
		edicao = edicaoClient.getEdicaoAtual();
		emailCandidato = entrevistaClient.getCandidatoEmail(edicao);
	}

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao tentar deletar entrevista por email sem autenticação")
    @Tag("Regression")
    void testDeletarEntrevistaPorEmailSemAutenticacao() {

        entrevistaClient.deletarEntrevistaPorEmailSemAutenticacao(emailCandidato)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
   }
}
