package br.com.dbccompany.vemser.tests.avaliacao;

import client.AvaliacaoClient;
import client.CandidatoClient;
import client.EdicaoClient;
import client.FormularioClient;
import client.InscricaoClient;
import factory.AvaliacaoDataFactory;
import models.avaliacao.AvaliacaoCriacaoModel;
import models.avaliacao.AvaliacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Endpoint de remoção de avaliação")
class DeletarAvaliacaoTest{
    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final InscricaoClient inscricaoClient = new InscricaoClient();
    private static final AvaliacaoClient avaliacaoClient = new AvaliacaoClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static CandidatoCriacaoResponseModel candidatoCadastrado;
    private static InscricaoModel inscricaoCadastrada;
    private static AvaliacaoModel avaliacaoCadastrada;
    private static AvaliacaoCriacaoModel avaliacao;
    @BeforeAll
    public static void setUp(){
        candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);
        inscricaoCadastrada = inscricaoClient.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);
        avaliacao = AvaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), true);
        avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao, true)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(AvaliacaoModel.class);
    }

    @AfterAll
    public static void setpDelete(){
        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao());
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
    }

    @Test
    @DisplayName("Cenário 1: Deve deletar avaliação com sucesso")
    @Tag("Functional")
    public void testDeveDeletarAvaliacaoComSucesso() {
        avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), true)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 2: Tentar deletar avaliação sem autenticação")
    @Tag("Functional")
    public void testTentarDeletarAvaliacaoSemAutenticacao() {
        avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
        avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), true);
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 3: Tentar deletar avaliação com id de avaliação negativo")
    @Tag("Regression")
    public void testTentarDeletarAvaliacaoIdAvaliacaoNegativo(){
        avaliacaoClient.deletarAvaliacao(-1, true)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("Avaliação não encontrada!"));
    }
}
