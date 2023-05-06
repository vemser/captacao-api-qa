package br.com.dbccompany.vemser.tests.inscricao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;
import service.InscricaoService;

@DisplayName("Endpoint de listagem de inscrição por id")
public class ListarInscricaoPorIdTest extends BaseTest {

    private static InscricaoService inscricaoService = new InscricaoService();
    private static CandidatoService candidatoService = new CandidatoService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista inscrição por id com sucesso")
    public void testListarInscricaoPorIdComSucesso() {

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

        InscricaoModel inscricaoListada = inscricaoService.listarInscricaoPorId(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(InscricaoModel.class);

        var deletarInscricao = inscricaoService.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);


        Assertions.assertNotNull(inscricaoListada);
        Assertions.assertEquals(inscricaoCadastrada.getIdInscricao(), inscricaoListada.getIdInscricao());
        Assertions.assertEquals(inscricaoCadastrada.getCandidato().getIdCandidato(), inscricaoListada.getCandidato().getIdCandidato());
        Assertions.assertEquals(inscricaoCadastrada.getCandidato().getFormulario().getIdFormulario(), inscricaoListada.getCandidato().getFormulario().getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando lista inscrição por id sem autenticação")
    public void testListarInscricaoPorIdSemAutenticacao() {

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

        var inscricaoListada = inscricaoService.listarInscricaoPorIdSemAutenticacao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarInscricao = inscricaoService.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
