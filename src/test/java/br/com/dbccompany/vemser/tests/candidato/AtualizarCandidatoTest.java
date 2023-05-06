package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.CandidatoDataFactory;
import dataFactory.FormularioDataFactory;
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
import service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de atualização de candidato")
public class AtualizarCandidatoTest extends BaseTest {

    private static TrilhaService trilhaService = new TrilhaService();
    private FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private FormularioService formularioService = new FormularioService();
    private EdicaoService edicaoService = new EdicaoService();
    private LinguagemService linguagemService = new LinguagemService();
    private CandidatoDataFactory candidatoDataFactory = new CandidatoDataFactory();
    private CandidatoService candidatoService = new CandidatoService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar candidato com sucesso")
    public void testAtualizarCandidatoComSucesso() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                            .statusCode(HttpStatus.SC_OK)
                            .extract()
                            .as(TrilhaModel[].class))
                            .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormularioComFormularioEntity(formulario);

        EdicaoModel edicaoCriada = edicaoService.criarEdicao();
        LinguagemModel linguagemCriada = linguagemService.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        CandidatoModel candidatoCadastrado = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoModel.class);


        CandidatoCriacaoModel candidatoCriadoComNovoNome = candidatoDataFactory.candidatoComNovoNome(candidatoCriado);

        CandidatoCriacaoResponseModel candidatoAtualizado = candidatoService.atualizarCandidato(candidatoCadastrado.getIdCandidato(), candidatoCriadoComNovoNome)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        var deletarCandidato = candidatoService.deletarCandidato(candidatoCadastrado.getIdCandidato())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        edicaoService.deletarEdicao(edicaoCriada.getIdEdicao());

        var deletarFormulario = formularioService.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario())
                        .then()
                                .statusCode(HttpStatus.SC_NOT_FOUND);

        Assertions.assertNotNull(candidatoAtualizado);
        Assertions.assertEquals(candidatoCadastrado.getIdCandidato(), candidatoAtualizado.getIdCandidato());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar candidato sem autenticação")
    public void testAtualizarCandidatoSemAutenticacao() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                            .statusCode(HttpStatus.SC_OK)
                            .extract()
                            .as(TrilhaModel[].class))
                            .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormulario(listaDeNomeDeTrilhas.get(0));
        formularioService.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicaoCriada = edicaoService.criarEdicao();
        LinguagemModel linguagemCriada = linguagemService.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        CandidatoModel candidatoCadastrado = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoModel.class);


        CandidatoCriacaoModel candidatoCriadoComNovoEmail = candidatoDataFactory.candidatoComNovoEmail(candidatoCriado);

        var candidatoAtualizado = candidatoService.atualizarCandidatoSemAutenticacao(candidatoCadastrado.getIdCandidato(), candidatoCriadoComNovoEmail)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);


        var deletarCandidato = candidatoService.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
