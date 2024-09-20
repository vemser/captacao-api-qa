package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.linguagem.LinguagemClient;
import client.trilha.TrilhaClient;
import factory.candidato.CandidatoDataFactory;
import factory.edicao.EdicaoDataFactory;
import factory.formulario.FormularioDataFactory;
import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.candidato.CandidatoModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.linguagem.LinguagemModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de atualização de candidato")
class AtualizarCandidatoTest {

    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private final FormularioClient formularioClient = new FormularioClient();
    private final EdicaoClient edicaoClient = new EdicaoClient();
    private final LinguagemClient linguagemClient = new LinguagemClient();
    private final CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar candidato com sucesso")
    void testAtualizarCandidatoComSucesso() {

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
        LinguagemModel linguagemCriada = new LinguagemModel("Java");

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoModel.class);


        CandidatoCriacaoModel candidatoCriadoComNovoNome = CandidatoDataFactory.candidatoComNovoNome(candidatoCriado);

        CandidatoCriacaoResponseModel candidatoAtualizado = candidatoClient.atualizarCandidato(candidatoCadastrado.getIdCandidato(), candidatoCriadoComNovoNome)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        edicaoClient.deletarEdicao(edicaoCriada.getIdEdicao());

        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario())
                        .then()
                                .statusCode(HttpStatus.SC_NOT_FOUND);

        Assertions.assertNotNull(candidatoAtualizado);
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoAtualizado.getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar candidato sem autenticação")
    void testAtualizarCandidatoSemAutenticacao() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                            .statusCode(HttpStatus.SC_OK)
                            .extract()
                            .as(TrilhaModel[].class))
                            .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormulario(listaDeNomeDeTrilhas.get(0));
        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao(edicao);
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoModel.class);


        CandidatoCriacaoModel candidatoCriadoComNovoEmail = CandidatoDataFactory.candidatoComNovoEmail(candidatoCriado);

        var candidatoAtualizado = candidatoClient.atualizarCandidatoSemAutenticacao(candidatoCadastrado.getIdCandidato(), candidatoCriadoComNovoEmail)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);


        var deletarCandidato = candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
