package br.com.dbccompany.vemser.tests.prova;

import client.candidato.CandidatoClient;
import client.prova.ProvaClient;
import factory.prova.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de data da prova do candidato")
class AtualizarDataProvaTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final ProvaClient provaClient = new ProvaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 quando atualiza data da prova do candidato com sucesso")
    void testAtualizarDataProvaComSucesso() {

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

        ProvaCriacaoModel provaDataAtualizada = ProvaDataFactory.provaComNovaData(prova);

        var dataDaProvaAtualizada = provaClient.atualizarDataProva(candidatoCadastrado.getIdCandidato(), provaDataAtualizada)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando atualiza data da prova do candidato sem autenticação")
    void testAtualizarDataProvaSemAutenticacao() {

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

        ProvaCriacaoModel provaDataAtualizada = ProvaDataFactory.provaComNovaData(prova);

        var dataDaProvaAtualizada = provaClient.atualizarDataProvaSemAutenticacao(candidatoCadastrado.getIdCandidato(), provaDataAtualizada)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
