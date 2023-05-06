package br.com.dbccompany.vemser.tests.prova;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.ProvaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;
import service.ProvaService;

@DisplayName("Endpoint de atualização de data da prova do candidato")
public class AtualizarDataProvaTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static ProvaDataFactory provaDataFactory = new ProvaDataFactory();
    private static ProvaService provaService = new ProvaService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 quando atualiza data da prova do candidato com sucesso")
    public void testAtualizarDataProvaComSucesso() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        ProvaCriacaoModel prova = provaDataFactory.provaValida();

        ProvaCriacaoResponseModel provaCriada = provaService.criarProva(candidatoCadastrado.getIdCandidato(), prova)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(ProvaCriacaoResponseModel.class);

        ProvaCriacaoModel provaDataAtualizada = provaDataFactory.provaComNovaData(prova);

        var dataDaProvaAtualizada = provaService.atualizarDataProva(candidatoCadastrado.getIdCandidato(), provaDataAtualizada)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando atualiza data da prova do candidato sem autenticação")
    public void testAtualizarDataProvaSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        ProvaCriacaoModel prova = provaDataFactory.provaValida();

        ProvaCriacaoResponseModel provaCriada = provaService.criarProva(candidatoCadastrado.getIdCandidato(), prova)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(ProvaCriacaoResponseModel.class);

        ProvaCriacaoModel provaDataAtualizada = provaDataFactory.provaComNovaData(prova);

        var dataDaProvaAtualizada = provaService.atualizarDataProvaSemAutenticacao(candidatoCadastrado.getIdCandidato(), provaDataAtualizada)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
