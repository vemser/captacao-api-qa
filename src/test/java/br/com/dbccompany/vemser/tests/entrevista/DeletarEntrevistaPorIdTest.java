package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de entrevista por id")
class DeletarEntrevistaPorIdTest{

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao tentar deletar entrevista por id sem autenticação")
    @Tag("Regression")
    void testDeletarEntrevistaPorIdSemAutenticacao() {

        int idEntrevista = entrevistaClient.listarTodasAsEntrevistas()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("[0].idEntrevista");

        entrevistaClient.deletarEntrevistaPorIdSemAutenticacao(idEntrevista)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
   }
}
