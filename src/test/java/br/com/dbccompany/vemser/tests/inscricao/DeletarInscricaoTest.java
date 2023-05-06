package br.com.dbccompany.vemser.tests.inscricao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;
import service.InscricaoService;

@DisplayName("Endpoint de remoção de inscrição")
public class DeletarInscricaoTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static InscricaoService inscricaoService = new InscricaoService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar inscrição com sucesso")
    public void testDeletarInscricaoComSucesso() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        InscricaoModel inscricaoCadastrada = inscricaoService.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);

        var deletarInscricao = inscricaoService.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar inscrição sem autenticação")
    public void testDeletarInscricaoSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        InscricaoModel inscricaoCadastrada = inscricaoService.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);

        var deletarInscricaoSemAutenticacao = inscricaoService.deletarInscricaoSemAutenticacao(inscricaoCadastrada.getIdInscricao())
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarInscricaoAutenticado = inscricaoService.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
