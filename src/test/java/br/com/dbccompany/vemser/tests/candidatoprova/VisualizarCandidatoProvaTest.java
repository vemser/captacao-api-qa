package br.com.dbccompany.vemser.tests.candidatoprova;

import client.candidatoprova.CandidatoProvaClient;
import client.prova.ProvaClient;
import factory.candidatoprova.CandidatoProvaDataFactory;
import factory.prova.ProvaDataFactory;
import io.restassured.response.Response;
import models.candidatoprova.CandidatoProvaModel;
import models.candidatoprova.CandidatoProvaResponse;
import models.prova.ProvaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
@DisplayName("Endpoint de visualizar prova-cadastro")
public class VisualizarCandidatoProvaTest {

    private static final CandidatoProvaClient candidatoProvaClient = new CandidatoProvaClient();
    private static final ProvaClient provaClient = new ProvaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando mostrar prova ao instrutor com sucesso")
    void testVisualizarProvaInstrutor() {

        ProvaResponse prova = provaClient.criarProva(ProvaDataFactory.provaValida()).as(ProvaResponse.class);

        candidatoProvaClient.visualizarProvaInstrutor(prova.getId())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                ;

    }

}
