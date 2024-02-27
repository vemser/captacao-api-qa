package br.com.dbccompany.vemser.tests.avaliacao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.AvaliacaoDataFactory;
import models.avaliacao.AvaliacaoCriacaoModel;
import models.avaliacao.AvaliacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.AvaliacaoClient;
import client.CandidatoClient;
import client.InscricaoClient;

@DisplayName("Endpoint de remoção de avaliação")
public class DeletarAvaliacaoTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static InscricaoClient inscricaoClient = new InscricaoClient();
    private static AvaliacaoDataFactory avaliacaoDataFactory = new AvaliacaoDataFactory();
    private static AvaliacaoClient avaliacaoClient = new AvaliacaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 quando deleta avaliação com sucesso")
    public void testDeletarAvaliacaoComSucesso() {

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

        var deletarAvaliacao = avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        var deletarAvaliacaoJaDeletada = avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao())
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando deleta avaliação sem autenticação")
    public void testDeletarAvaliacaoSemAutenticacao() {

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


        var deletarAvaliacaoSemAutenticacao = avaliacaoClient.deletarAvaliacaoSemAutenticacao(avaliacaoCadastrada.getIdAvaliacao())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarAvaliacao = avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
