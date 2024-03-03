package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import client.prova.ProvaClient;
import factory.nota.NotaDataFactory;
import factory.parecer.ParecerTecnicoDataFactory;
import factory.prova.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.parecerTecnico.ParecerTecnicoModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de parecer técnico")
class AtualizarParecerTecnicoTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final ProvaClient provaClient = new ProvaClient();
    private static final NotaDataFactory notaDataFactory = new NotaDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando atualiza parecer técnico com sucesso")
    void testAtualizarParecerTecnicoComSucesso() {
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

        ParecerTecnicoModel parecerTecnico = ParecerTecnicoDataFactory.parecerTecnicoValido();

        CandidatoCriacaoResponseModel candidatoParecerTecnicoAtualizado = candidatoClient.atualizarParecerTecnico(candidatoCadastrado.getIdCandidato(), parecerTecnico)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        Assertions.assertNotNull(candidatoParecerTecnicoAtualizado);
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoParecerTecnicoAtualizado.getIdCandidato());
        Assertions.assertEquals(parecerTecnico.getParecerTecnico(), candidatoParecerTecnicoAtualizado.getParecerTecnico());
        Assertions.assertEquals(parecerTecnico.getNotaTecnica(), candidatoParecerTecnicoAtualizado.getNotaEntrevistaTecnica());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando atualiza parecer técnico sem autenticação")
    void testAtualizarParecerTecnicoSemAutenticacao() {
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

        ParecerTecnicoModel parecerTecnico = ParecerTecnicoDataFactory.parecerTecnicoValido();

        var candidatoParecerTecnicoAtualizado = candidatoClient.atualizarParecerTecnicoSemAutenticacao(candidatoCadastrado.getIdCandidato(), parecerTecnico)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
