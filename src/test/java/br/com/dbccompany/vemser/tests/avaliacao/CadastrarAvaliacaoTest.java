package br.com.dbccompany.vemser.tests.avaliacao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.AvaliacaoDataFactory;
import models.avaliacao.AvaliacaoCriacaoModel;
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

@DisplayName("Endpoint de cadastro de avaliação")
public class CadastrarAvaliacaoTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static InscricaoClient inscricaoClient = new InscricaoClient();
    private static AvaliacaoClient avaliacaoClient = new AvaliacaoClient();
    private static AvaliacaoDataFactory avaliacaoDataFactory = new AvaliacaoDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 ao cadastrar avaliação com sucesso")
    public void testCadastrarAvaliacaoComSucesso() {

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


        Assertions.assertNotNull(avaliacaoCadastrada);
        Assertions.assertEquals(inscricaoCadastrada.getIdInscricao(), avaliacaoCadastrada.getInscricao().getIdInscricao());
        Assertions.assertEquals(inscricaoCadastrada.getCandidato().getIdCandidato(), avaliacaoCadastrada.getInscricao().getCandidato().getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao cadastrar avaliação sem autenticação")
    public void testCadastrarAvaliacaoSemAutenticacao() {

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

        var avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacaoSemAutenticacao(avaliacao)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
