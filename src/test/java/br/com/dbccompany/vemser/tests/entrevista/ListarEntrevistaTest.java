package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de listagem de entrevistas")
class ListarEntrevistaTest {
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista as entrevistas cadastradas com sucesso")
    @Tag("Regression")
    void testListarEntrevistasCadastradasComSucesso() {

        var listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel[].class);

        Assertions.assertNotNull(listaDeEntrevistas);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando lista as entrevistas sem estar autenticado")
    @Tag("Regression")
    void testListarEntrevistasSemAutenticacao() {

        var listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistasSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }

}
