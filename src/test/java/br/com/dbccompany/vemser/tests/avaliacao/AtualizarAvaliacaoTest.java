package br.com.dbccompany.vemser.tests.avaliacao;

import client.avaliacao.AvaliacaoClient;
import client.candidato.CandidatoClient;
import client.inscricao.InscricaoClient;
import factory.avaliacao.AvaliacaoDataFactory;
import models.avaliacao.AvaliacaoCriacaoModel;
import models.avaliacao.AvaliacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de avaliação")
class AtualizarAvaliacaoTest {

    private final CandidatoClient candidatoClient = new CandidatoClient();
    private final InscricaoClient inscricaoClient = new InscricaoClient();
    private final AvaliacaoClient avaliacaoClient = new AvaliacaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar avaliação com sucesso")
    void testAtualizarAvaliacaoComSucesso() {

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

        boolean aprovado = true;
        AvaliacaoCriacaoModel avaliacao = AvaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), aprovado);

        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(AvaliacaoModel.class);

        aprovado = false;
        AvaliacaoCriacaoModel avaliacaoParaAtualizacao = AvaliacaoDataFactory.avaliacaoAtualizada(avaliacao, aprovado);

        AvaliacaoModel avaliacaoAtualizada = avaliacaoClient.atualizarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoParaAtualizacao)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(AvaliacaoModel.class);

        var deletarAvaliacao = avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertNotNull(avaliacaoAtualizada);
        Assertions.assertEquals(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoAtualizada.getIdAvaliacao());
        Assertions.assertEquals(avaliacaoCadastrada.getInscricao().getIdInscricao(), avaliacaoAtualizada.getInscricao().getIdInscricao());
        Assertions.assertEquals(avaliacaoCadastrada.getInscricao().getCandidato().getIdCandidato(), avaliacaoAtualizada.getInscricao().getCandidato().getIdCandidato());
        Assertions.assertEquals(avaliacaoCadastrada.getInscricao().getCandidato().getFormulario().getIdFormulario(), avaliacaoAtualizada.getInscricao().getCandidato().getFormulario().getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar avaliação sem autenticação")
    void testAtualizarAvaliacaoSemAutenticacao() {
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

        boolean aprovado = true;
        AvaliacaoCriacaoModel avaliacao = AvaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), aprovado);

        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(AvaliacaoModel.class);

        aprovado = false;
        AvaliacaoCriacaoModel avaliacaoParaAtualizacao = AvaliacaoDataFactory.avaliacaoAtualizada(avaliacao, aprovado);

        var avaliacaoAtualizada = avaliacaoClient.atualizarAvaliacaoSemAutenticacao(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoParaAtualizacao)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
