package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de entrevista por email")
class DeletarEntrevistaPorEmailTest {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao tentar deletar entrevista por email sem autenticação")
    void testDeletarEntrevistaPorEmailSemAutenticacao() {

        String email = entrevistaClient.listarTodasAsEntrevistas()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("[0].candidatoEmail");


        entrevistaClient.deletarEntrevistaPorEmailSemAutenticacao(email)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
   }
}
