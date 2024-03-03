package br.com.dbccompany.vemser.tests.prova;

import client.prova.ProvaClient;
import factory.prova.ProvaDataFactory;
import models.prova.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de editar prova")
class EditarProvaTest {
    private static final ProvaClient provaClient = new ProvaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando edita questões da prova com sucesso")
    void testEditarQuestoesDaProvaComSucesso() {
        // Cria uma prova inicial válida
        ProvaCriacaoModel provaInicial = ProvaDataFactory.provaValida();

        // Cria a prova
        ProvaResponse criadaResponse = provaClient.criarProva(provaInicial)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        // Cria uma nova instância de ProvaCriacaoModel com as questões editadas
        ProvaEditarQuestoesModel provaEditada = ProvaDataFactory.provaValidaComQuestoesEditadas();

        // Edita as questões da prova
        ProvaResponse editadaResponse = provaClient.editarQuestoesProva(criadaResponse.getId(), provaEditada)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando edita duracao da prova com sucesso")
    void testEditarDuracaoDaProvaComSucesso() {
        // Cria uma prova inicial válida
        ProvaCriacaoModel provaInicial = ProvaDataFactory.provaValida();

        // Cria a prova
        ProvaResponse criadaResponse = provaClient.criarProva(provaInicial)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        // Cria uma nova instância de ProvaCriacaoModel com as questões editadas
        ProvaEditarDuracaoModel provaEditada = ProvaDataFactory.provaEditarDuracaoProva();

        // Edita as questões da prova
        ProvaResponse editadaResponse = provaClient.editarDuracaoProva(criadaResponse.getId(), provaEditada)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 200 quando edita dados da prova com sucesso")
    void testEditarDadosDaProvaComSucesso() {
        // Cria uma prova inicial válida
        ProvaCriacaoModel provaInicial = ProvaDataFactory.provaValida();

        // Cria a prova
        ProvaResponse criadaResponse = provaClient.criarProva(provaInicial)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        // Cria uma nova instância de ProvaCriacaoModel com as questões editadas
        ProvaEditarDadosModel provaEditada = ProvaDataFactory.provaEditarDadosProva();

        // Edita as questões da prova
        ProvaResponse editadaResponse = provaClient.editarDadosProva(criadaResponse.getId(), provaEditada)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }
}
