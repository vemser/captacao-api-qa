package br.com.dbccompany.vemser.tests.entrevista;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.EntrevistaDataFactory;
import io.qameta.allure.Feature;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;
import service.EntrevistaService;

import java.util.Locale;

@Feature("Atualizar entrevista")
public class AtualizarEntrevistaTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static EntrevistaDataFactory entrevistaDataFactory = new EntrevistaDataFactory();
    private static EntrevistaService entrevistaService = new EntrevistaService();
    private static Faker faker = new Faker(new Locale("pt-BR"));

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar entrevista com sucesso")
    public void testAtualizarEntrevistaComSucesso() {

        String observacoes = faker.lorem().sentence(3);
        Boolean avaliado = false;
        String statusEntrevista = "CONFIRMADA";

        CandidatoCriacaoResponseModel candidatoCriado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;

        EntrevistaCriacaoModel entrevistaCriada = entrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaService.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        EntrevistaCriacaoModel entrevistaComNovosDados = entrevistaDataFactory.entrevistaCriacaoValidaComDadosAtualizados(entrevistaCriada, observacoes, avaliado);

        EntrevistaCriacaoResponseModel entrevistaAtualizada = entrevistaService.atualizarEntrevista(entrevistaCadastrada.getIdEntrevista(), statusEntrevista, entrevistaComNovosDados)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        var deletarEntrevista = entrevistaService.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertNotNull(entrevistaAtualizada);
        Assertions.assertEquals(observacoes, entrevistaAtualizada.getObservacoes());
        Assertions.assertEquals(statusEntrevista, entrevistaAtualizada.getLegenda());
        Assertions.assertEquals(entrevistaCadastrada.getIdEntrevista(), entrevistaAtualizada.getIdEntrevista());
        Assertions.assertEquals(entrevistaCadastrada.getCandidatoDTO().getIdCandidato(), entrevistaAtualizada.getCandidatoDTO().getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar entrevista sem estar autenticado")
    public void testAtualizarEntrevistaSemAutenticacao() {

        String observacoes = faker.lorem().sentence(3);
        Boolean avaliado = false;
        String statusEntrevista = "CONFIRMADA";

        CandidatoCriacaoResponseModel candidatoCriado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;

        EntrevistaCriacaoModel entrevistaCriada = entrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaService.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        EntrevistaCriacaoModel entrevistaComNovosDados = entrevistaDataFactory.entrevistaCriacaoValidaComDadosAtualizados(entrevistaCriada, observacoes, avaliado);

        var entrevistaAtualizada = entrevistaService.atualizarEntrevistaSemAutenticacao(entrevistaCadastrada.getIdEntrevista(), statusEntrevista, entrevistaComNovosDados)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarEntrevista = entrevistaService.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
