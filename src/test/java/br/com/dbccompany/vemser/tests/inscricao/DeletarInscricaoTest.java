package br.com.dbccompany.vemser.tests.inscricao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.CandidatoClient;
import client.InscricaoClient;

@DisplayName("Endpoint de remoção de inscrição")
public class DeletarInscricaoTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static InscricaoClient inscricaoClient = new InscricaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar inscrição com sucesso")
    public void testDeletarInscricaoComSucesso() {

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

        var deletarInscricao = inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar inscrição sem autenticação")
    public void testDeletarInscricaoSemAutenticacao() {

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

        var deletarInscricaoSemAutenticacao = inscricaoClient.deletarInscricaoSemAutenticacao(inscricaoCadastrada.getIdInscricao())
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarInscricaoAutenticado = inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
