package br.com.dbccompany.vemser.tests.inscricao;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.CandidatoClient;
import client.InscricaoClient;

@DisplayName("Endpoint de cadastro de inscrição")
public class CadastrarInscricaoTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static InscricaoClient inscricaoClient = new InscricaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 ao cadastrar inscrição com sucesso")
    public void testCadastrarInscricaoComSucesso() {

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


        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), inscricaoCadastrada.getCandidato().getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), inscricaoCadastrada.getCandidato().getFormulario().getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao cadastrar inscrição sem autenticação")
    public void testCadastrarInscricaoSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);


        InscricaoModel inscricaoCadastrada = inscricaoClient.cadastrarInscricaoSemAutenticacao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);


        var deletarInscricao = inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), inscricaoCadastrada.getCandidato().getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), inscricaoCadastrada.getCandidato().getFormulario().getIdFormulario());
    }
}
