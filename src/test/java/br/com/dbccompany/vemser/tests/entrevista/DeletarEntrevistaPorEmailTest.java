package br.com.dbccompany.vemser.tests.entrevista;

import client.edicao.EdicaoClient;
import client.entrevista.EntrevistaClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de entrevista por email")
class DeletarEntrevistaPorEmailTest {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao tentar deletar entrevista por email sem autenticação")
    @Tag("Regression")
    void testDeletarEntrevistaPorEmailSemAutenticacao() {

        String edicao = edicaoClient.listaEdicaoAtualAutenticacao()
                .then()
                .extract().asString();

        String email = entrevistaClient.listarTodasAsEntrevistas(edicao)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("[0].candidatoEmail");


        entrevistaClient.deletarEntrevistaPorEmailSemAutenticacao(email)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
   }
}
