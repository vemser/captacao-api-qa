package br.com.dbccompany.vemser.tests.entrevista;

import client.EntrevistaClient;
import factory.EntrevistaDataFactory;
import io.restassured.response.Response;
import models.entrevista.EntrevistaCriacaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de marcação de entrevista")
class CadastrarEntrevistaTest  {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao cadastrar entrevista sem autenticação")
    @Tag("Regression")
    void testCadastrarEntrevistaSemAutenticacao() {
		
        Boolean candidatoAvaliado = true;

		Response response = EntrevistaDataFactory.buscarTodasEntrevistas();
		String emailEntrevista = response.path("[0].candidatoDTO.email");

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailEntrevista, candidatoAvaliado);

        entrevistaClient.cadastrarEntrevistaSemAutenticacao(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
