package br.com.dbccompany.vemser.tests.candidato;

import client.CandidatoClient;
import client.EdicaoClient;
import client.FormularioClient;
import factory.CandidatoDataFactory;
import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.candidato.CandidatoModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Endpoint de atualização de candidato")
class AtualizarCandidatoTest {

    private final FormularioClient formularioClient = new FormularioClient();
    private final EdicaoClient edicaoClient = new EdicaoClient();
    private final CandidatoClient candidatoClient = new CandidatoClient();

    private FormularioCriacaoResponseModel formularioCriado;
    private EdicaoModel edicaoCriada;
    private CandidatoCriacaoModel candidatoCriado;
    private CandidatoModel candidatoCadastrado;
    private CandidatoCriacaoModel candidatoCriadoComNovoNome;
    private CandidatoCriacaoResponseModel candidatoAtualizado;
    private CandidatoCriacaoModel candidatoCriadoComNovoEmail;

    @BeforeEach
    void setUp(){
        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        listaDeNomeDeTrilhas.add("FRONTEND");
        formularioCriado = CandidatoDataFactory.criarFormularioValido(listaDeNomeDeTrilhas, formularioClient);
        edicaoCriada = CandidatoDataFactory.criarEdicaoValida(edicaoClient);
        candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), "java");
        candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
                .as(CandidatoModel.class);
    }

    @AfterEach
    void deleteData(){
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);

        edicaoClient.deletarEdicao(edicaoCriada.getIdEdicao());
    }

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar candidato com sucesso")
    @Tag("Regression")
    void testAtualizarCandidatoComSucesso() {

        candidatoCriadoComNovoNome = CandidatoDataFactory.candidatoComNovoNome(candidatoCriado);

        candidatoAtualizado =
        candidatoClient.atualizarCandidato(candidatoCadastrado.getIdCandidato(), candidatoCriadoComNovoNome)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
                .as(CandidatoCriacaoResponseModel.class);

        Assertions.assertNotNull(candidatoAtualizado);
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoAtualizado.getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar candidato sem autenticação")
    @Tag("Functional")
    void testAtualizarCandidatoSemAutenticacao() {

        candidatoCriadoComNovoEmail = CandidatoDataFactory.candidatoComNovoEmail(candidatoCriado);

        candidatoClient.atualizarCandidatoSemAutenticacao(candidatoCadastrado.getIdCandidato(), candidatoCriadoComNovoEmail)
        .then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
