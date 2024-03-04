package br.com.dbccompany.vemser.tests.prova;

import client.prova.ProvaClient;
import factory.prova.ProvaDataFactory;
import models.prova.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

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
        ProvaEditarModel provaEditada = ProvaDataFactory.provaValidaComQuestoesEditadas(provaInicial);

        // Edita as questões da prova
        ProvaResponse editadaResponse = provaClient.editarQuestoesProva(criadaResponse.getId(), provaEditada)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(ProvaResponse.class)
                ;

        assertAll(
                () -> Assertions.assertEquals("Editado com sucesso", editadaResponse.getMensagem()),
                () -> Assertions.assertNotNull(editadaResponse.getId())
        );

        provaClient.deletarProva(editadaResponse.getId());

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
        ProvaEditarModel provaEditada = ProvaDataFactory.provaEditarDuracaoProva(provaInicial);

        // Edita as questões da prova
        ProvaResponse editadaResponse = provaClient.editarDuracaoProva(criadaResponse.getId(), provaEditada)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(ProvaResponse.class)
                ;

        assertAll(
                () -> Assertions.assertEquals("Editado com sucesso", editadaResponse.getMensagem()),
                () -> Assertions.assertNotNull(editadaResponse.getId())
        );

        provaClient.deletarProva(editadaResponse.getId());

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
        ProvaEditarModel provaEditada = ProvaDataFactory.provaEditarDadosProva(provaInicial);

        // Edita as questões da prova
        ProvaResponse editadaResponse = provaClient.editarDadosProva(criadaResponse.getId(), provaEditada)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(ProvaResponse.class)
                ;

        assertAll(
                () -> Assertions.assertEquals("Editado com sucesso", editadaResponse.getMensagem()),
                () -> Assertions.assertNotNull(editadaResponse.getId())
        );

        provaClient.deletarProva(editadaResponse.getId());

    }
}
