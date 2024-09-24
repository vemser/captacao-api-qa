package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.trilha.TrilhaClient;
import factory.candidato.CandidatoDataFactory;
import factory.edicao.EdicaoDataFactory;
import factory.formulario.FormularioDataFactory;
import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de delete fisico de candidato")
public class DeleteFisiscoCandidatoTest {

    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private final FormularioClient formularioClient = new FormularioClient();
    private final EdicaoClient edicaoClient = new EdicaoClient();
    private final CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar candidato com sucesso")
    @Tag("Functional")
    void testDeleteFisicoDeCandidatoComSucesso() {

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

        candidatoClient.deleteFisicoCandidato(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar candidato sem autenticação")
    @Tag("Regression")
    void testDeleteFisicoDeCandidatoSemAutenticacao() {

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
        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao(edicao);

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
                .as(CandidatoModel.class);

        candidatoClient.deleteFisicoCandidatoSemAutenticacao(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_FORBIDDEN);

        candidatoClient.deleteFisicoCandidato(candidatoCadastrado.getIdCandidato())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 400 ao deletar candidato com id invalido")
    @Tag("Regression")
    void testDeletarFisicoDeCandidatoComIDInvalido() {

        candidatoClient.deleteFisicoCandidato(0)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("message", Matchers.equalTo("Candidato não encontrado."));
    }
}
