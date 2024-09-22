package br.com.dbccompany.vemser.tests.avaliacao;

import client.avaliacao.AvaliacaoClient;
import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.inscricao.InscricaoClient;
import factory.avaliacao.AvaliacaoDataFactory;
import models.avaliacao.AvaliacaoCriacaoModel;
import models.avaliacao.AvaliacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Endpoint de atualização de avaliação")
class AtualizarAvaliacaoTest {
    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final InscricaoClient inscricaoClient = new InscricaoClient();
    private static final AvaliacaoClient avaliacaoClient = new AvaliacaoClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final FormularioClient formularioClient = new FormularioClient();

    @Test
    @DisplayName("Cenário 1: Deve atualizar avaliação com sucesso")
    void testDeveAtualizarAvaliacaoComSucesso() {
        CandidatoCriacaoResponseModel candidatoCadastradoUm = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);
        CandidatoCriacaoResponseModel candidatoCadastradoDois = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);
        InscricaoModel inscricaoCadastradaUm = inscricaoClient.cadastrarInscricao(candidatoCadastradoUm.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);
        InscricaoModel inscricaoCadastradaDois = inscricaoClient.cadastrarInscricao(candidatoCadastradoDois.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);
        AvaliacaoCriacaoModel avaliacao = AvaliacaoDataFactory.avaliacaoValida(inscricaoCadastradaUm.getIdInscricao(), true);
        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao, true)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(AvaliacaoModel.class);
        AvaliacaoCriacaoModel avaliacaoParaAtualizar = AvaliacaoDataFactory.avaliacaoAtualizada(avaliacao, false, inscricaoCadastradaDois.getIdInscricao());
        AvaliacaoModel avaliacaoAtualizada = avaliacaoClient.atualizarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoParaAtualizar, true)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(AvaliacaoModel.class);
        assertAll(
                () -> assertNotNull(avaliacaoAtualizada),
                () -> assertEquals(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoAtualizada.getIdAvaliacao()),
                () -> assertNotEquals(avaliacaoCadastrada.getInscricao().getIdInscricao(), avaliacaoAtualizada.getInscricao().getIdInscricao()),
                () -> assertEquals(avaliacaoCadastrada.getInscricao().getCandidato().getIdCandidato(), avaliacaoAtualizada.getInscricao().getCandidato().getIdCandidato()),
                () -> assertEquals(avaliacaoCadastrada.getInscricao().getCandidato().getFormulario().getIdFormulario(), avaliacaoAtualizada.getInscricao().getCandidato().getFormulario().getIdFormulario())
        );
        avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), true);

        inscricaoClient.deletarInscricao(inscricaoCadastradaUm.getIdInscricao());
        inscricaoClient.deletarInscricao(inscricaoCadastradaDois.getIdInscricao());

        candidatoClient.deletarCandidato(candidatoCadastradoUm.getIdCandidato());
        candidatoClient.deletarCandidato(candidatoCadastradoDois.getIdCandidato());

        formularioClient.deletarFormulario(candidatoCadastradoUm.getFormulario().getIdFormulario());
        formularioClient.deletarFormulario(candidatoCadastradoDois.getFormulario().getIdFormulario());

        edicaoClient.deletarEdicao(candidatoCadastradoUm.getEdicao().getIdEdicao());
        edicaoClient.deletarEdicao(candidatoCadastradoDois.getEdicao().getIdEdicao());
    }

    @Test
    @DisplayName("Cenário 2: Tentar atualizar avaliação sem autenticação")
    void testTentarAtualizarAvaliacaoSemAutenticacao() {
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
        AvaliacaoCriacaoModel avaliacao = AvaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), true);
        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao, true)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(AvaliacaoModel.class);
        AvaliacaoCriacaoModel avaliacaoParaAtualizar = AvaliacaoDataFactory.avaliacaoAtualizada(avaliacao, false, inscricaoCadastrada.getIdInscricao());
        avaliacaoClient.atualizarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoParaAtualizar, false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao());
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
    }

    @Test
    @DisplayName("Cenário 3: Tentar atualizar avaliação com id de avaliação negativo")
    public void testTentarAtualizarAvaliacaoIdAvaliacaoNegativo(){
        CandidatoCriacaoResponseModel candidatoCadastradoUm = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);
        InscricaoModel inscricaoCadastrada = inscricaoClient.cadastrarInscricao(candidatoCadastradoUm.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);
        AvaliacaoCriacaoModel avaliacao = AvaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), true);
        AvaliacaoCriacaoModel avaliacaoParaAtualizar = AvaliacaoDataFactory.avaliacaoAtualizada(avaliacao, false, inscricaoCadastrada.getIdInscricao());
        avaliacaoClient.atualizarAvaliacao(-1, avaliacaoParaAtualizar, true)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("Avaliação não encontrada!"));
        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao());
        candidatoClient.deletarCandidato(candidatoCadastradoUm.getIdCandidato());
        formularioClient.deletarFormulario(candidatoCadastradoUm.getFormulario().getIdFormulario());
        edicaoClient.deletarEdicao(candidatoCadastradoUm.getEdicao().getIdEdicao());
    }

    @Test
    @DisplayName("Cenário 4: Tentar atualizar avaliação com id de inscrição negativo")
    public void testTentarAtualizarAvaliacaoIdInscricaoNegativo(){
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
        AvaliacaoCriacaoModel avaliacao = AvaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), true);
        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao, true)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(AvaliacaoModel.class);

        AvaliacaoCriacaoModel avaliacaoParaAtualizar = AvaliacaoDataFactory.avaliacaoAtualizarIdInscricaoNegativo(avaliacao, false);
        avaliacaoClient.atualizarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoParaAtualizar, true)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("Avaliação não encontrada!"));
        avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), true);
        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao());
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
    }
}