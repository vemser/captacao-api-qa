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

		EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado);

		EntrevistaCriacaoModel entrevistaComNovosDados = EntrevistaDataFactory.entrevistaCriacaoValidaComDadosAtualizados(entrevistaCriada, observacoes, avaliado);

		EntrevistaCriacaoResponseModel entrevistaAtualizada = entrevistaClient.atualizarEntrevista(1, statusEntrevista, entrevistaComNovosDados)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.extract()
				.as(EntrevistaCriacaoResponseModel.class);

        Assertions.assertNotNull(entrevistaAtualizada);
        Assertions.assertEquals(observacoes, entrevistaAtualizada.getObservacoes());
        Assertions.assertEquals(statusEntrevista, entrevistaAtualizada.getLegenda());
        Assertions.assertEquals("CONFIRMADA", entrevistaAtualizada.getLegenda().toUpperCase());
        Assertions.assertNotNull(entrevistaAtualizada.getIdEntrevista());
		Assertions.assertNotEquals(entrevistaCriada.getDataEntrevista(), entrevistaAtualizada.getDataEntrevista());
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

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado);

        EntrevistaCriacaoModel entrevistaComNovosDados = EntrevistaDataFactory.entrevistaCriacaoValidaComDadosAtualizados(entrevistaCriada, observacoes, avaliado);

        entrevistaClient.atualizarEntrevistaSemAutenticacao(1, statusEntrevista, entrevistaComNovosDados)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
