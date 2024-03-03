package br.com.dbccompany.vemser.tests.prova;

import client.prova.ProvaClient;
import factory.prova.ProvaDataFactory;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de excluir prova")
public class DeletarProvaTest {
    private static final ProvaClient provaClient = new ProvaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando exclui uma prova com sucesso")
    void testExcluirProvaComSucesso() {
        // Cria uma prova inicial válida
        ProvaCriacaoModel provaInicial = ProvaDataFactory.provaValida();

        // Cria a prova
        ProvaResponse response = provaClient.criarProva(provaInicial)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        // Edita as questões da prova
        ProvaResponse provaExcluida = provaClient.deletarProva(response.getId())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 400 quando tenta excluir uma prova sem autenticação")
    void testExcluirProvaSemAutenticacao() {
        // Cria uma prova inicial válida
        ProvaCriacaoModel provaInicial = ProvaDataFactory.provaValida();

        // Cria a prova
        ProvaResponse response = provaClient.criarProva(provaInicial)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        // Edita as questões da prova
        ProvaResponse provaExcluida = provaClient.deletarProvaSemAutenticacao(response.getId())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(ProvaResponse.class);
    }
}
