package br.com.dbccompany.vemser.tests.inscricao;

import client.candidato.CandidatoClient;
import client.inscricao.InscricaoClient;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de listagem de inscrição por id")
class ListarInscricaoPorIdTest {

    private static final InscricaoClient inscricaoClient = new InscricaoClient();
    private static final CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista inscrição por id com sucesso")
    @Tag("Regression")
    void testListarInscricaoPorIdComSucesso() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);


        InscricaoModel inscricaoCadastrada = inscricaoClient.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);

        InscricaoModel inscricaoListada = inscricaoClient.listarInscricaoPorId(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(InscricaoModel.class);

        var deletarInscricao = inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);


        Assertions.assertNotNull(inscricaoListada);
        Assertions.assertEquals(inscricaoCadastrada.getIdInscricao(), inscricaoListada.getIdInscricao());
        Assertions.assertEquals(inscricaoCadastrada.getCandidato().getIdCandidato(), inscricaoListada.getCandidato().getIdCandidato());
        Assertions.assertEquals(inscricaoCadastrada.getCandidato().getFormulario().getIdFormulario(), inscricaoListada.getCandidato().getFormulario().getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando lista inscrição por id sem autenticação")
    @Tag("Regression")
    void testListarInscricaoPorIdSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);


        InscricaoModel inscricaoCadastrada = inscricaoClient.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);

        var inscricaoListada = inscricaoClient.listarInscricaoPorIdSemAutenticacao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarInscricao = inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
