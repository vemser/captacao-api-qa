package br.com.dbccompany.vemser.tests.prova;

import client.candidato.CandidatoClient;
import client.prova.ProvaClient;
import factory.prova.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de marcação da prova do candidato")
class CadastrarProvaTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final ProvaClient provaClient = new ProvaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando cadastra prova com sucesso")
    void testCriaProvaParaCandidatoComSucesso() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        ProvaCriacaoModel prova = ProvaDataFactory.provaValida();

        ProvaCriacaoResponseModel provaCriada = provaClient.criarProva(candidatoCadastrado.getIdCandidato(), prova)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(ProvaCriacaoResponseModel.class);

        Assertions.assertNotNull(provaCriada);
        Assertions.assertEquals(prova.getDataInicio(), provaCriada.getDataInicio());
        Assertions.assertEquals(prova.getDataFinal(), provaCriada.getDataFinal());
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), provaCriada.getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando cadastra prova sem autenticacao")
    void testCriaProvaParaCandidatoSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        ProvaCriacaoModel prova = ProvaDataFactory.provaValida();

        var provaCriada = provaClient.criarProvaSemAutenticacao(candidatoCadastrado.getIdCandidato(), prova)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
