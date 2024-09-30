package br.com.dbccompany.vemser.tests.inscricao;

import client.CandidatoClient;
import client.InscricaoClient;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de cadastro de inscrição")
class CadastrarInscricaoTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final InscricaoClient inscricaoClient = new InscricaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 ao cadastrar inscrição com sucesso")
    @Tag("Regression")
    void testCadastrarInscricaoComSucesso() {

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


        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), inscricaoCadastrada.getCandidato().getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), inscricaoCadastrada.getCandidato().getFormulario().getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao cadastrar inscrição sem autenticação")
    @Tag("Regression")
    void testCadastrarInscricaoSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
                .as(CandidatoCriacaoResponseModel.class);


        InscricaoModel inscricaoCadastrada = inscricaoClient.cadastrarInscricaoSemAutenticacao(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
                .as(InscricaoModel.class);

        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), inscricaoCadastrada.getCandidato().getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), inscricaoCadastrada.getCandidato().getFormulario().getIdFormulario());
    }
}
