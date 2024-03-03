package br.com.dbccompany.vemser.tests.prova;

import client.prova.ProvaClient;
import factory.prova.ProvaDataFactory;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de editar prova")
public class EditarProvaTest {
    private static final ProvaClient provaClient = new ProvaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando edita prova com sucesso")
    void testEditarProvaComSucesso() {

        ProvaCriacaoModel provaCriada = ProvaDataFactory.provaValida();
        ProvaCriacaoModel provaEditada = ProvaDataFactory.provaValida();

        ProvaResponse criadaResponse = provaClient.criarProva(provaCriada)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        ProvaCriacaoModel editadaResponse = provaClient.editarQuestoesProva(criadaResponse.getId(), provaEditada)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaCriacaoModel.class);

    }
}
