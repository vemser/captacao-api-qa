package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
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

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida("email@mail.com", candidatoAvaliado);

        var entrevistaCadastrada = entrevistaClient.cadastrarEntrevistaSemAutenticacao(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
