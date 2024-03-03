package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import client.prova.ProvaClient;
import factory.nota.NotaDataFactory;
import factory.parecer.ParecerComportamentalDataFactory;
import factory.prova.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.parecerComportamental.ParecerComportamentalModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de parecer comportamental")
class AtualizarParecerComportamentalTest{

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final ProvaClient provaClient = new ProvaClient();
    private static final NotaDataFactory notaDataFactory = new NotaDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando atualiza parecer comportamental do candidato com sucesso")
    void testAtualizarParecerComportamentalComSucesso() {
        Double nota = 80.0;

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                .statusCode(201)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        ProvaCriacaoModel prova = ProvaDataFactory.provaValida();
        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        CandidatoCriacaoResponseModel candidatoComNotaAtualizada = candidatoClient
                .atualizarNotaCandidato(
                        candidatoCadastrado.getIdCandidato(),
                        notaDataFactory.notaValida(nota)
                )
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        ParecerComportamentalModel parecerComportamental = ParecerComportamentalDataFactory.parecerComportamentalValido();

        CandidatoCriacaoResponseModel candidatoParecerComportamentalAtualizado =
                candidatoClient.atualizarParecerComportamental(
                                candidatoCadastrado.getIdCandidato(),
                                parecerComportamental)
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(CandidatoCriacaoResponseModel.class);

        Assertions.assertNotNull(candidatoParecerComportamentalAtualizado);
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoParecerComportamentalAtualizado.getIdCandidato());
        Assertions.assertEquals(parecerComportamental.getParecerComportamental(), candidatoParecerComportamentalAtualizado.getParecerComportamental());
        Assertions.assertEquals(parecerComportamental.getNotaComportamental(), candidatoParecerComportamentalAtualizado.getNotaEntrevistaComportamental());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando atualiza parecer comportamental do candidato sem autenticação")
    void testAtualizarParecerComportamentalSemAutenticacao() {
        Double nota = 80.0;

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        ProvaCriacaoModel prova = ProvaDataFactory.provaValida();
        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        CandidatoCriacaoResponseModel candidatoComNotaAtualizada = candidatoClient
                .atualizarNotaCandidato(
                        candidatoCadastrado.getIdCandidato(),
                        notaDataFactory.notaValida(nota)
                )
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);
        ParecerComportamentalModel parecerComportamental = ParecerComportamentalDataFactory.parecerComportamentalValido();

        var candidatoParecerComportamentalAtualizado =
                candidatoClient.atualizarParecerComportamentalSemAutenticacao(
                                candidatoCadastrado.getIdCandidato(), parecerComportamental)
                        .then()
                        .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
