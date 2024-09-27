package br.com.dbccompany.vemser.tests.entrevista;

import client.EdicaoClient;
import client.EntrevistaClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de entrevista por id")
class DeletarEntrevistaPorIdTest{

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao tentar deletar entrevista por id sem autenticação")
    @Tag("Regression")
    void testDeletarEntrevistaPorIdSemAutenticacao() {

        String edicao = edicaoClient.listaEdicaoAtualAutenticacao()
                .then()
                .extract().asString();


        int idEntrevista = entrevistaClient.listarTodasAsEntrevistas(edicao)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("[0].idEntrevista");

        entrevistaClient.deletarEntrevistaPorIdSemAutenticacao(idEntrevista)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
   }
}
