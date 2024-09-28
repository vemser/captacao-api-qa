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

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Endpoint de cadastro de avaliação")
class CadastrarAvaliacaoTest {
    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final InscricaoClient inscricaoClient = new InscricaoClient();
    private static final AvaliacaoClient avaliacaoClient = new AvaliacaoClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static CandidatoCriacaoResponseModel candidatoCadastrado;
    private static InscricaoModel inscricaoCadastrada;
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
    }

    @AfterAll
    public static void setpDelete(){
        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao());
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
    }


    @Test
    @DisplayName("Cenário 1: Deve cadastrar avaliação com sucesso")
    @Tag("Functional")
    public void testCadastrarAvaliacaoComSucesso() {
        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao, true)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(AvaliacaoModel.class);
        assertAll(
                () -> assertNotNull(avaliacaoCadastrada.getIdAvaliacao()),
                () -> assertEquals(avaliacaoCadastrada.getInscricao().getIdInscricao(), inscricaoCadastrada.getIdInscricao())
        );
        avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), true);
    }

    @Test
    @DisplayName("Cenário 2: Tentar cadastrar avaliação sem autenticação")
    @Tag("Regression")
    public void testTentarCadastrarAvaliacaoSemAutenticacao() {
        avaliacaoClient.cadastrarAvaliacao(avaliacao, false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 3: Tentar cadastrar avaliação com id de inscrição negativo")
    @Tag("Regression")
    public void testTentarCadastrarAvaliacaoComIdInscricaoNegativo(){
        AvaliacaoCriacaoModel avaliacao = AvaliacaoDataFactory.avaliacaoValida(-1, true);
        avaliacaoClient.cadastrarAvaliacao(avaliacao, true)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("message", equalTo("ID_Inscrição inválido"));
    }

    @Test
    @DisplayName("Cenário 4: Validar contrato cadastrar avaliação")
    @Tag("Contract")
    public void testValidarContratoCadastrarAvaliacao(){
        AvaliacaoModel avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao, true)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body(matchesJsonSchemaInClasspath("schemas/avaliacao/Cadastrar_avaliacao.json"))
                    .extract()
                    .as(AvaliacaoModel.class);
        avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), true);
    }
}