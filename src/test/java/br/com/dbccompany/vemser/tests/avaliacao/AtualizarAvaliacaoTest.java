package br.com.dbccompany.vemser.tests.avaliacao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.AvaliacaoDataFactory;
import models.avaliacao.AvaliacaoCriacaoModel;
import models.avaliacao.AvaliacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.AvaliacaoService;
import service.CandidatoService;
import service.InscricaoService;

@DisplayName("Endpoint de atualização de avaliação")
public class AtualizarAvaliacaoTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static InscricaoService inscricaoService = new InscricaoService();
    private static AvaliacaoDataFactory avaliacaoDataFactory = new AvaliacaoDataFactory();
    private static AvaliacaoService avaliacaoService = new AvaliacaoService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar avaliação com sucesso")
    public void testAtualizarAvaliacaoComSucesso() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);


        InscricaoModel inscricaoCadastrada = inscricaoService.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(InscricaoModel.class);

        Boolean aprovado = true;
        AvaliacaoCriacaoModel avaliacao = avaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), aprovado);

        AvaliacaoModel avaliacaoCadastrada = avaliacaoService.cadastrarAvaliacao(avaliacao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(AvaliacaoModel.class);

        aprovado = false;
        AvaliacaoCriacaoModel avaliacaoParaAtualizacao = avaliacaoDataFactory.avaliacaoAtualizada(avaliacao, aprovado);

        AvaliacaoModel avaliacaoAtualizada = avaliacaoService.atualizarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoParaAtualizacao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(AvaliacaoModel.class);

        var deletarAvaliacao = avaliacaoService.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao())
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
    public void testAtualizarAvaliacaoSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);


        InscricaoModel inscricaoCadastrada = inscricaoService.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(InscricaoModel.class);

        Boolean aprovado = true;
        AvaliacaoCriacaoModel avaliacao = avaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), aprovado);

        AvaliacaoModel avaliacaoCadastrada = avaliacaoService.cadastrarAvaliacao(avaliacao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(AvaliacaoModel.class);

        aprovado = false;
        AvaliacaoCriacaoModel avaliacaoParaAtualizacao = avaliacaoDataFactory.avaliacaoAtualizada(avaliacao, aprovado);

        var avaliacaoAtualizada = avaliacaoService.atualizarAvaliacaoSemAutenticacao(avaliacaoCadastrada.getIdAvaliacao(), avaliacaoParaAtualizacao)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarAvaliacao = avaliacaoService.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
