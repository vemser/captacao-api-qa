package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de entrevista")
class AtualizarEntrevistaTest  {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();


    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao atualizar entrevista sem estar autenticado")
    void testAtualizarEntrevistaSemAutenticacao() {

        String statusEntrevista = "CONFIRMADA";

        EntrevistaCriacaoResponseModel[] listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistas()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(EntrevistaCriacaoResponseModel[].class);

        int primeiraEntrevistaId = listaDeEntrevistas[0].getIdEntrevista();

        String emailDoCandidato = "email@email.com";
        Boolean candidatoAvaliado = true;

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado);

        entrevistaClient.atualizarEntrevistaSemAutenticacao(primeiraEntrevistaId, statusEntrevista, entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }
}
