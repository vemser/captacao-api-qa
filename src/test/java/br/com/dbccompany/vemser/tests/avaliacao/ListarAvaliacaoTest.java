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
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Endpoint de listagem de avaliação")
class ListarAvaliacaoTest{
    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final InscricaoClient inscricaoClient = new InscricaoClient();
    private static final AvaliacaoClient avaliacaoClient = new AvaliacaoClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static CandidatoCriacaoResponseModel candidatoCadastrado;
    private static InscricaoModel inscricaoCadastrada;
    private static AvaliacaoModel avaliacaoCadastrada;
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
        avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao, true)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(AvaliacaoModel.class);
    }

    @AfterAll
    public static void setpDelete(){
        avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao(), true);
        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao());
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
    }
    @Test
    @DisplayName("Cenário 1: Deve listar toda avaliação com sucesso")
    @Tag("Regression")
    public void testListarTodaAvaliacaoComSucesso() {
        avaliacaoClient.listarTodaAvaliacao(true)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                        .body("totalElementos", notNullValue())
                        .body("elementos", notNullValue());
    }

    @Test
    @DisplayName("Cenário 2: Tentar listar toda avaliação sem autenticação")
    @Tag("Regression")
    public void testTentarListarTodaAvaliacaoSemAutenticacao() {
        avaliacaoClient.listarTodaAvaliacao(false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Cenário 3: Validar contrato listar toda avaliação")
    @Tag("Contract")
    public void testValidarContratoListarTodaAvaliacao(){
        avaliacaoClient.listarTodaAvaliacao(true)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/avaliacao/Listar_toda_avaliacao.json"));
    }
}