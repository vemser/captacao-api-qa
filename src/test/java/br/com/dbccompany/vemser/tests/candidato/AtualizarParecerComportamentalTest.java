package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.NotaDataFactory;
import factory.ParecerComportamentalDataFactory;
import factory.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.parecerComportamental.ParecerComportamentalModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.CandidatoClient;
import client.ProvaClient;

@DisplayName("Endpoint de atualização de parecer comportamental")
public class AtualizarParecerComportamentalTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static ProvaDataFactory provaDataFactory = new ProvaDataFactory();
    private static ProvaClient provaClient = new ProvaClient();
    private static NotaDataFactory notaDataFactory = new NotaDataFactory();
    private static ParecerComportamentalDataFactory parecerComportamentalDataFactory = new ParecerComportamentalDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando atualiza parecer comportamental do candidato com sucesso")
    public void testAtualizarParecerComportamentalComSucesso() {
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

        ParecerComportamentalModel parecerComportamental = parecerComportamentalDataFactory.parecerComportamentalValido();

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
    public void testAtualizarParecerComportamentalSemAutenticacao() {
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

        ParecerComportamentalModel parecerComportamental = parecerComportamentalDataFactory.parecerComportamentalValido();

        var candidatoParecerComportamentalAtualizado =
                candidatoClient.atualizarParecerComportamentalSemAutenticacao(
                                candidatoCadastrado.getIdCandidato(),
                                parecerComportamental)
                        .then()
                        .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
