package br.com.dbccompany.vemser.tests.inscricao;

import client.CandidatoClient;
import client.InscricaoClient;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoListaResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de listagem de inscrições")
class ListarInscricaoTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final InscricaoClient inscricaoClient = new InscricaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista inscrições com sucesso")
    @Tag("Functional")
    void testListarInscricoesComSucesso() {

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

        InscricaoListaResponseModel listaInscricoes = inscricaoClient.listaUltimaInscricao()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(InscricaoListaResponseModel.class);

        InscricaoModel inscricaoListada = listaInscricoes.getElementos().get(0);

        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);


        Assertions.assertNotNull(inscricaoListada);
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), inscricaoListada.getCandidato().getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), inscricaoListada.getCandidato().getFormulario().getIdFormulario());
        Assertions.assertEquals(inscricaoCadastrada.getIdInscricao(), inscricaoListada.getIdInscricao());
    }
}
