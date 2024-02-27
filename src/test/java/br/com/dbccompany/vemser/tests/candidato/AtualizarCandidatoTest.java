package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import client.*;
import factory.CandidatoDataFactory;
import factory.FormularioDataFactory;
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
public class AtualizarCandidatoTest extends BaseTest {

    private static TrilhaClient trilhaClient = new TrilhaClient();
    private FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private FormularioClient formularioClient = new FormularioClient();
    private EdicaoClient edicaoClient = new EdicaoClient();
    private LinguagemClient linguagemClient = new LinguagemClient();
    private CandidatoDataFactory candidatoDataFactory = new CandidatoDataFactory();
    private CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar candidato com sucesso")
    public void testAtualizarCandidatoComSucesso() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                            .statusCode(HttpStatus.SC_OK)
                            .extract()
                            .as(TrilhaModel[].class))
                            .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoModel.class);


        CandidatoCriacaoModel candidatoCriadoComNovoNome = candidatoDataFactory.candidatoComNovoNome(candidatoCriado);

        CandidatoCriacaoResponseModel candidatoAtualizado = candidatoClient.atualizarCandidato(candidatoCadastrado.getIdCandidato(), candidatoCriadoComNovoNome)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        var deletarCandidato = candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        edicaoClient.deletarEdicao(edicaoCriada.getIdEdicao());

        var deletarFormulario = formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario())
                        .then()
                                .statusCode(HttpStatus.SC_NOT_FOUND);

        Assertions.assertNotNull(candidatoAtualizado);
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoAtualizado.getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar candidato sem autenticação")
    public void testAtualizarCandidatoSemAutenticacao() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoModel.class);


        CandidatoCriacaoModel candidatoCriadoComNovoEmail = candidatoDataFactory.candidatoComNovoEmail(candidatoCriado);

        var candidatoAtualizado = candidatoClient.atualizarCandidatoSemAutenticacao(candidatoCadastrado.getIdCandidato(), candidatoCriadoComNovoEmail)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);


        var deletarCandidato = candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
