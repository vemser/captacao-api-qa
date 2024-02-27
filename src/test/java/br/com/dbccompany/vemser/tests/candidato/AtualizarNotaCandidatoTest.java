package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import client.*;
import factory.NotaDataFactory;
import factory.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de nota de candidato")
public class AtualizarNotaCandidatoTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static NotaDataFactory notaDataFactory = new NotaDataFactory();
    private static ProvaClient provaClient = new ProvaClient();
    private static ProvaDataFactory provaDataFactory = new ProvaDataFactory();
    private static FormularioClient formularioClient = new FormularioClient();
    private static EdicaoClient edicaoClient = new EdicaoClient();
    private static LinguagemClient linguagemClient = new LinguagemClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando atualiza nota do candidato com sucesso")
    public void testAtualizarNotaDoCandidatoComSucesso() {
        Double nota = 80.0;

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        ProvaCriacaoModel prova = provaDataFactory.provaValida();
        ProvaCriacaoResponseModel provaCriada = provaClient.criarProva(candidatoCadastrado.getIdCandidato(), prova)
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
    public void testAtualizarNotaDoCandidatoSemAutenticacao() {
        Double nota = 80.0;

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        ProvaCriacaoModel prova = provaDataFactory.provaValida();
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
