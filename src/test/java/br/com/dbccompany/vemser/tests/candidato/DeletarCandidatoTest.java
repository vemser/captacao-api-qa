package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.candidato.CandidatoCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;

public class DeletarCandidatoTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 quando deleta candidato com sucesso")
    public void testDeletarCandidatoComSucesso() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        var candidatoDeletado = candidatoService.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando deleta candidato sem autenticação")
    public void testDeletarCandidatoSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        var candidatoDeletado = candidatoService.deletarCandidatoSemAutenticacao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
