package br.com.dbccompany.vemser.tests.candidato;

import client.CandidatoClient;
import client.EdicaoClient;
import client.FormularioClient;
import client.TrilhaClient;
import factory.CandidatoDataFactory;
import factory.EdicaoDataFactory;
import factory.FormularioDataFactory;
import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de busca de candidato por Email")
class BuscarCandidatoPorEmailTest {

    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando busca candidato por email com sucesso")
    @Tag("Regression")
    void testBuscarCandidatoPorEmailComSucesso() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
                .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao(edicao);

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
                .as(CandidatoModel.class);

        CandidatoModel candidatoBuscado = candidatoClient.buscarCandidatoPorEmail(candidatoCadastrado.getEmail())
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
                .as(CandidatoModel.class);

        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoBuscado.getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getNome(), candidatoBuscado.getNome());
        Assertions.assertEquals(candidatoCadastrado.getDataNascimento(), candidatoBuscado.getDataNascimento());
        Assertions.assertEquals(candidatoCadastrado.getCpf(), candidatoBuscado.getCpf());
        Assertions.assertEquals(candidatoCadastrado.getTelefone(), candidatoBuscado.getTelefone());
        Assertions.assertEquals(candidatoCadastrado.getRg(), candidatoBuscado.getRg());
        Assertions.assertEquals(candidatoCadastrado.getCpf(), candidatoBuscado.getCpf());
        Assertions.assertEquals(candidatoCadastrado.getEstado(), candidatoBuscado.getEstado());
        Assertions.assertEquals(candidatoCadastrado.getCidade(), candidatoBuscado.getCidade());
        Assertions.assertEquals(candidatoCadastrado.getPcd(), candidatoBuscado.getPcd());
        Assertions.assertEquals(candidatoCadastrado.getObservacoes(), candidatoBuscado.getObservacoes());
        Assertions.assertEquals(candidatoCadastrado.getNotaEntrevistaComportamental(), candidatoBuscado.getNotaEntrevistaComportamental());
        Assertions.assertEquals(candidatoCadastrado.getNotaEntrevistaTecnica(), candidatoBuscado.getNotaEntrevistaTecnica());
        Assertions.assertEquals(candidatoCadastrado.getAtivo(), candidatoBuscado.getAtivo());
        Assertions.assertEquals(candidatoCadastrado.getParecerComportamental(), candidatoBuscado.getParecerComportamental());
        Assertions.assertEquals(candidatoCadastrado.getParecerTecnico(), candidatoBuscado.getParecerTecnico());
        Assertions.assertEquals(candidatoCadastrado.getMedia(), candidatoBuscado.getMedia());
        Assertions.assertEquals(candidatoCadastrado.getEdicao().getIdEdicao(), candidatoBuscado.getEdicao().getIdEdicao());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), candidatoBuscado.getFormulario().getIdFormulario());

        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve validar o contrato de busca de candidatos por email no sistema")
    @Tag("Regression")
    void testValidarContratoBuscarCandidatoPorEmail() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
                .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao(edicao);

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
                .as(CandidatoModel.class);

        CandidatoModel candidatoBuscado = candidatoClient.buscarCandidatoPorEmail(candidatoCadastrado.getEmail())
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(matchesJsonSchemaInClasspath("schemas/candidato/BuscarCandidatoPorEmail.json"))
            .extract()
                .as(CandidatoModel.class);

        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 quando busca candidato por email sem autenticação")
    @Tag("Regression")
    void testBuscarCandidatoPorEmailSemAutenticacao() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
                .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao(edicao);

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
                .as(CandidatoModel.class);

        candidatoClient.buscarCandidatoPorEmailSemAutenticacao(candidatoCadastrado.getEmail())
        .then()
            .statusCode(HttpStatus.SC_FORBIDDEN);

        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 quando busca candidato com email inválido")
    @Tag("Regression")
    void testBuscarCandidatoPorEmailInvalido() {

        String emailInvalido = "invalido@email.com";

        CandidatoModel candidatoBuscado = candidatoClient.buscarCandidatoPorEmail(emailInvalido)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(CandidatoModel.class);

        Assertions.assertEquals(candidatoBuscado.getMessage(), "Candidato com o e-mail especificado não existe");
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 400 quando busca candidato com email vazio")
    @Tag("Regression")
    void testBuscarCandidatoPorEmailVazio() {

        String emailVazio = "";

        CandidatoModel candidatoBuscado = candidatoClient.buscarCandidatoPorEmail(emailVazio)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(CandidatoModel.class);

        Assertions.assertEquals(candidatoBuscado.getMessage(), "Candidato com o e-mail especificado não existe");
    }
}



