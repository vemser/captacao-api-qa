package br.com.dbccompany.vemser.tests.prova;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.CandidatoClient;
import client.ProvaClient;

import java.util.Random;

@DisplayName("Endpoint de atualização da nota do candidato")
public class AtualizarNotaProvaTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static ProvaDataFactory provaDataFactory = new ProvaDataFactory();
    private static ProvaClient provaClient = new ProvaClient();
    private static Random random = new Random();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando atualiza nota da prova com sucesso")
    public void testAtualizarNotaDaProvaComSucesso() {
        Integer novaNota = random.nextInt(100);

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

        var provaComNotaAtualizada = provaClient.atualizarNotaProva(provaCriada.getIdCandidato(), novaNota)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando atualiza nota da prova sem autenticação")
    public void testAtualizarNotaDaProvaSemAutenticacao() {
        Integer novaNota = random.nextInt(100);

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

        var provaComNotaAtualizada = provaClient.atualizarNotaProvaSemAutenticacao(provaCriada.getIdCandidato(), novaNota)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
