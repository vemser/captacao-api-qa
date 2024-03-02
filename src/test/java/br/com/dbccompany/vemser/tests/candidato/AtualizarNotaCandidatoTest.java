package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import client.prova.ProvaClient;
import client.questao.QuestaoClient;
import factory.nota.NotaDataFactory;
import factory.prova.ProvaDataFactory;
import factory.questoes.QuestoesDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaCriacaoResponseModel;
import models.questoes.QuestaoObjetivaModel;
import models.questoes.QuestaoObjetivaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de nota de candidato")
class AtualizarNotaCandidatoTest{

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final NotaDataFactory notaDataFactory = new NotaDataFactory();
    private static final ProvaClient provaClient = new ProvaClient();
    private static final QuestaoClient questaoClient = new QuestaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando atualiza nota do candidato com sucesso")
    void testAtualizarNotaDoCandidatoComSucesso() {
        Double nota = 80.0;

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        QuestaoObjetivaModel questaoObjetivaModel = QuestoesDataFactory.questaoObjetivaValida();
        QuestaoObjetivaResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva()


        ProvaCriacaoModel prova = ProvaDataFactory.provaValida();

        ProvaCriacaoResponseModel provaCriada = provaClient.criarProva(prova)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(ProvaCriacaoResponseModel.class);

        CandidatoCriacaoResponseModel candidatoComNotaAtualizada = candidatoClient
                .atualizarNotaCandidato(
                        candidatoCadastrado.getIdCandidato(),
                        notaDataFactory.notaValida(nota)
                )
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);


        Assertions.assertNotNull(candidatoComNotaAtualizada);
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoComNotaAtualizada.getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), candidatoComNotaAtualizada.getFormulario().getIdFormulario());
        Assertions.assertEquals(nota, candidatoComNotaAtualizada.getNota());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando atualiza nota do candidato sem autenticação")
    void testAtualizarNotaDoCandidatoSemAutenticacao() {
        Double nota = 80.0;

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

        var candidatoComNotaAtualizada = candidatoClient
                .atualizarNotaCandidatoSemAutenticacao(
                        candidatoCadastrado.getIdCandidato(),
                        notaDataFactory.notaValida(nota)
                )
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
