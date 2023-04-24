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

public class CadastrarInscricaoTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static InscricaoService inscricaoService = new InscricaoService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 ao cadastrar inscrição com sucesso")
    public void testCadastrarInscricaoComSucesso() {

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


        var deletarInscricao = inscricaoService.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);


        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), inscricaoCadastrada.getCandidato().getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), inscricaoCadastrada.getCandidato().getFormulario().getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao cadastrar inscrição sem autenticação")
    public void testCadastrarInscricaoSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);


        InscricaoModel inscricaoCadastrada = inscricaoService.cadastrarInscricaoSemAutenticacao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(InscricaoModel.class);


        var deletarInscricao = inscricaoService.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), inscricaoCadastrada.getCandidato().getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), inscricaoCadastrada.getCandidato().getFormulario().getIdFormulario());
    }
}
