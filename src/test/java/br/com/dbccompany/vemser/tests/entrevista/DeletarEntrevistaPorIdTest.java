package br.com.dbccompany.vemser.tests.entrevista;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.EntrevistaDataFactory;
import io.qameta.allure.Feature;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;
import service.EntrevistaService;

@Feature("Deletar entrevista por id")
public class DeletarEntrevistaPorIdTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static EntrevistaDataFactory entrevistaDataFactory = new EntrevistaDataFactory();
    private static EntrevistaService entrevistaService = new EntrevistaService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar entrevista por id com sucesso")
    public void testDeletarEntrevistaPorIdComSucesso() {

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

        var response = entrevistaService.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        var buscaEntrevistaDeletada = entrevistaService.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar entrevista por id sem autenticação")
    public void testDeletarEntrevistaPorIdSemAutenticacao() {

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

        var response = entrevistaService.deletarEntrevistaPorIdSemAutenticacao(entrevistaCadastrada.getIdEntrevista())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarEntrevista = entrevistaService.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
