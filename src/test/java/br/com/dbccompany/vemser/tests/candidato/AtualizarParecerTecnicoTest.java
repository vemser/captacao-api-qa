package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.NotaDataFactory;
import factory.ParecerTecnicoDataFactory;
import factory.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.parecerTecnico.ParecerTecnicoModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.CandidatoClient;
import client.ProvaClient;

@DisplayName("Endpoint de atualização de parecer técnico")
public class AtualizarParecerTecnicoTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static ParecerTecnicoDataFactory parecerTecnicoDataFactory = new ParecerTecnicoDataFactory();
    private static ProvaDataFactory provaDataFactory = new ProvaDataFactory();
    private static ProvaClient provaClient = new ProvaClient();
    private static NotaDataFactory notaDataFactory = new NotaDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando atualiza parecer técnico com sucesso")
    public void testAtualizarParecerTecnicoComSucesso() {
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

        ParecerTecnicoModel parecerTecnico = parecerTecnicoDataFactory.parecerTecnicoValido();

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
    public void testAtualizarParecerTecnicoSemAutenticacao() {
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

        ParecerTecnicoModel parecerTecnico = parecerTecnicoDataFactory.parecerTecnicoValido();

        var candidatoParecerTecnicoAtualizado = candidatoClient.atualizarParecerTecnicoSemAutenticacao(candidatoCadastrado.getIdCandidato(), parecerTecnico)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
