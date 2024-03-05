package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import models.candidato.CandidatoCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de candidato")
class DeletarCandidatoTest{

    private static final CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 quando deleta candidato com sucesso")
    void testDeletarCandidatoComSucesso() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        var candidatoDeletado = candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando deleta candidato sem autenticação")
    void testDeletarCandidatoSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        var candidatoDeletado = candidatoClient.deletarCandidatoSemAutenticacao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
