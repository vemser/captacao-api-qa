package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

@DisplayName("Endpoint de atualização de entrevista")
class AtualizarEntrevistaTest  {

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final Faker faker = new Faker(new Locale("pt-BR"));

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar entrevista com sucesso")
    void testAtualizarEntrevistaComSucesso() {

        String observacoes = faker.lorem().sentence(3);
        Boolean avaliado = false;
        String statusEntrevista = "CONFIRMADA";

        CandidatoCriacaoResponseModel candidatoCriado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;
        Integer idTrilha = candidatoCriado.getFormulario().getTrilhas().get(0).getIdTrilha();

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado, idTrilha);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaClient.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        EntrevistaCriacaoModel entrevistaComNovosDados = EntrevistaDataFactory.entrevistaCriacaoValidaComDadosAtualizados(entrevistaCriada, observacoes, avaliado);

        EntrevistaCriacaoResponseModel entrevistaAtualizada = entrevistaClient.atualizarEntrevista(entrevistaCadastrada.getIdEntrevista(), statusEntrevista, entrevistaComNovosDados)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        var deletarEntrevista = entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
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
    void testAtualizarEntrevistaSemAutenticacao() {

        String observacoes = faker.lorem().sentence(3);
        Boolean avaliado = false;
        String statusEntrevista = "CONFIRMADA";

        CandidatoCriacaoResponseModel candidatoCriado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;
        Integer idTrilha = candidatoCriado.getFormulario().getTrilhas().get(0).getIdTrilha();

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado, idTrilha);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaClient.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        EntrevistaCriacaoModel entrevistaComNovosDados = EntrevistaDataFactory.entrevistaCriacaoValidaComDadosAtualizados(entrevistaCriada, observacoes, avaliado);

        var entrevistaAtualizada = entrevistaClient.atualizarEntrevistaSemAutenticacao(entrevistaCadastrada.getIdEntrevista(), statusEntrevista, entrevistaComNovosDados)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarEntrevista = entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
