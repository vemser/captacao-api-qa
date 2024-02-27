package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import client.*;
import factory.CandidatoDataFactory;
import factory.FormularioDataFactory;
import models.candidato.CandidatoCriacaoModel;
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

@DisplayName("Endpoint de busca de candidato por CPF")
public class BuscarCandidatoPorCpfTest extends BaseTest {

    private static TrilhaClient trilhaClient = new TrilhaClient();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static FormularioClient formularioClient = new FormularioClient();
    private static EdicaoClient edicaoClient = new EdicaoClient();
    private static LinguagemClient linguagemClient = new LinguagemClient();
    private static CandidatoDataFactory candidatoDataFactory = new CandidatoDataFactory();
    private static CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando busca candidato por cpf com sucesso")
    public void testBuscarCandidatoPorCpfComSucesso() {

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

        CandidatoModel candidatoBuscado = candidatoClient.buscarCandidatoPorCpf(candidatoCadastrado.getCpf())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(CandidatoModel.class);

        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoBuscado.getIdCandidato());
        Assertions.assertEquals(candidatoCadastrado.getNome(), candidatoBuscado.getNome());
        Assertions.assertEquals(candidatoCadastrado.getDataNascimento(), candidatoBuscado.getDataNascimento());
        Assertions.assertEquals(candidatoCadastrado.getEmail(), candidatoBuscado.getEmail());
        Assertions.assertEquals(candidatoCadastrado.getTelefone(), candidatoBuscado.getTelefone());
        Assertions.assertEquals(candidatoCadastrado.getRg(), candidatoBuscado.getRg());
        Assertions.assertEquals(candidatoCadastrado.getCpf(), candidatoBuscado.getCpf());
        Assertions.assertEquals(candidatoCadastrado.getEstado(), candidatoBuscado.getEstado());
        Assertions.assertEquals(candidatoCadastrado.getCidade(), candidatoBuscado.getCidade());
        Assertions.assertEquals(candidatoCadastrado.getNota(), candidatoBuscado.getNota());
        Assertions.assertEquals(candidatoCadastrado.getPcd(), candidatoBuscado.getPcd());
        Assertions.assertEquals(candidatoCadastrado.getObservacoes(), candidatoBuscado.getObservacoes());
        Assertions.assertEquals(candidatoCadastrado.getNotaEntrevistaComportamental(), candidatoBuscado.getNotaEntrevistaComportamental());
        Assertions.assertEquals(candidatoCadastrado.getNotaEntrevistaTecnica(), candidatoBuscado.getNotaEntrevistaTecnica());
        Assertions.assertEquals(candidatoCadastrado.getAtivo(), candidatoBuscado.getAtivo());
        Assertions.assertEquals(candidatoCadastrado.getParecerComportamental(), candidatoBuscado.getParecerComportamental());
        Assertions.assertEquals(candidatoCadastrado.getParecerTecnico(), candidatoBuscado.getParecerTecnico());
        Assertions.assertEquals(candidatoCadastrado.getMedia(), candidatoBuscado.getMedia());

        candidatoCadastrado.getLinguagens().stream().forEach(linguagemCadastrada -> {
            candidatoBuscado.getLinguagens().stream().forEach(linguagemBuscada -> {
                Assertions.assertEquals(linguagemCadastrada.getIdLinguagem(), linguagemBuscada.getIdLinguagem());
                Assertions.assertEquals(linguagemCadastrada.getNome(), linguagemBuscada.getNome());
            });
        });

        Assertions.assertEquals(candidatoCadastrado.getEdicao().getIdEdicao(), candidatoBuscado.getEdicao().getIdEdicao());
        Assertions.assertEquals(candidatoCadastrado.getFormulario().getIdFormulario(), candidatoBuscado.getFormulario().getIdFormulario());

        var deletarCandidato = candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando busca candidato por cpf sem autenticação")
    public void testBuscarCandidatoPorCpfSemAutenticacao() {

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

        var response = candidatoClient.buscarCandidatoPorCpfSemAutenticacao(candidatoCadastrado.getCpf())
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarCandidato = candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
