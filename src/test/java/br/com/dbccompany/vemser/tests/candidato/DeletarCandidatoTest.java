package br.com.dbccompany.vemser.tests.candidato;

import client.CandidatoClient;
import client.EdicaoClient;
import client.FormularioClient;
import factory.CandidatoDataFactory;
import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Endpoint de delete de candidato")
class DeletarCandidatoTest {

    private final FormularioClient formularioClient = new FormularioClient();
    private final EdicaoClient edicaoClient = new EdicaoClient();
    private final CandidatoClient candidatoClient = new CandidatoClient();

    private FormularioCriacaoResponseModel formularioCriado;
    private EdicaoModel edicaoCriada;
    private CandidatoCriacaoModel candidatoCriado;
    private CandidatoModel candidatoCadastrado;

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

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar candidato com sucesso")
    @Tag("Functional")
    void testDeletarCandidatoComSucesso() {

        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar candidato sem autenticação")
    @Tag("Regression")
    void testDeletarCandidatoSemAutenticacao() {


        candidatoClient.deletarCandidatoSemAutenticacao(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_FORBIDDEN);

        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 400 ao deletar candidato com id invalido")
    @Tag("Regression")
    void testDeletarCandidatoComIDInvalido() {

        candidatoClient.deletarCandidato(0)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", Matchers.equalTo("Candidato não encontrado."));

        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}


