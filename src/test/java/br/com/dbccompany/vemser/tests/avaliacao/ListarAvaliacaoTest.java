package br.com.dbccompany.vemser.tests.avaliacao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.AvaliacaoDataFactory;
import models.avaliacao.AvaliacaoCriacaoModel;
import models.avaliacao.AvaliacaoListaResponseModel;
import models.avaliacao.AvaliacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.AvaliacaoClient;
import client.CandidatoClient;
import client.InscricaoClient;

@DisplayName("Endpoint de listagem de avaliação")
public class ListarAvaliacaoTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static InscricaoClient inscricaoClient = new InscricaoClient();
    private static AvaliacaoClient avaliacaoClient = new AvaliacaoClient();
    private static AvaliacaoDataFactory avaliacaoDataFactory = new AvaliacaoDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar avaliações com sucesso")
    public void testListarAvaliacoesComSucesso() {

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

        Boolean aprovado = true;
        AvaliacaoCriacaoModel avaliacao = avaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), aprovado);

        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(AvaliacaoModel.class);


        AvaliacaoListaResponseModel listaDeAvaliacoes = avaliacaoClient.listarUltimaAvaliacao()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(AvaliacaoListaResponseModel.class);

        AvaliacaoModel ultimaAvaliacao = listaDeAvaliacoes.getElementos().get(0);


        var deletarAvaliacao = avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);



        Assertions.assertNotNull(ultimaAvaliacao);
        Assertions.assertEquals(avaliacaoCadastrada.getIdAvaliacao(), ultimaAvaliacao.getIdAvaliacao());
        Assertions.assertEquals(avaliacaoCadastrada.getInscricao().getIdInscricao(), ultimaAvaliacao.getInscricao().getIdInscricao());
        Assertions.assertEquals(avaliacaoCadastrada.getInscricao().getCandidato().getIdCandidato(), ultimaAvaliacao.getInscricao().getCandidato().getIdCandidato());
        Assertions.assertEquals(avaliacaoCadastrada.getInscricao().getCandidato().getFormulario().getIdFormulario(), ultimaAvaliacao.getInscricao().getCandidato().getFormulario().getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao listar avaliações sem autenticação")
    public void testListarAvaliacoesSemAutenticacao() {

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

        Boolean aprovado = true;
        AvaliacaoCriacaoModel avaliacao = avaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), aprovado);

        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(AvaliacaoModel.class);


        var listaDeAvaliacoes = avaliacaoClient.listarUltimaAvaliacaoSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarAvaliacao = avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
