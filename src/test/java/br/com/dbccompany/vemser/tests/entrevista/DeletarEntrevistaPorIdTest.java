package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de entrevista")
class DeletarEntrevistaPorIdTest{

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao tentar deletar entrevista por id sem autenticação")
    void testDeletarEntrevistaPorIdSemAutenticacao() {

        entrevistaClient.deletarEntrevistaPorIdSemAutenticacao(1)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
