package br.com.dbccompany.vemser.tests.candidato;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.CandidatoDataFactory;
import dataFactory.FormularioDataFactory;
import io.qameta.allure.Feature;
import models.JSONFailureResponseWithArrayModel;
import models.JSONFailureResponseWithoutArrayModel;
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
import service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Feature("Cadastrar candidato")
public class CadastrarCandidatoTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static FormularioService formularioService = new FormularioService();
    private static EdicaoService edicaoService = new EdicaoService();
    private static TrilhaService trilhaService = new TrilhaService();
    private static LinguagemService linguagemService = new LinguagemService();
    private static CandidatoDataFactory candidatoDataFactory = new CandidatoDataFactory();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();


    @Test
    @DisplayName("Cenário 1: Deve retornar 200 e cadastrar candidato com sucesso")
    public void testCadastrarCandidatoComSucesso() {

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

        var deletarCandidato = candidatoService.deletarCandidato(candidatoCadastrado.getIdCandidato())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        edicaoService.deletarEdicao(edicaoCriada.getIdEdicao());



        Assertions.assertEquals(candidatoCriado.getNome().toLowerCase(), candidatoCadastrado.getNome().toLowerCase());
        Assertions.assertEquals(candidatoCriado.getDataNascimento(), candidatoCadastrado.getDataNascimento());
        Assertions.assertEquals(candidatoCriado.getEmail(), candidatoCadastrado.getEmail());
        Assertions.assertEquals(candidatoCriado.getTelefone(), candidatoCadastrado.getTelefone());
        Assertions.assertEquals(candidatoCriado.getRg(), candidatoCadastrado.getRg());
        Assertions.assertEquals(candidatoCriado.getCpf(), candidatoCadastrado.getCpf());
        Assertions.assertEquals(candidatoCriado.getEstado().toLowerCase(), candidatoCadastrado.getEstado().toLowerCase());
        Assertions.assertEquals(candidatoCriado.getCidade().toLowerCase(), candidatoCadastrado.getCidade().toLowerCase());
        Assertions.assertEquals(candidatoCriado.getPcd().toLowerCase(), candidatoCadastrado.getPcd().toLowerCase());
        Assertions.assertEquals(candidatoCriado.getAtivo().toLowerCase(), candidatoCadastrado.getAtivo().toLowerCase());
        Assertions.assertEquals(candidatoCriado.getLinguagens().size(), candidatoCadastrado.getLinguagens().size());
        Assertions.assertEquals(candidatoCriado.getEdicao().getIdEdicao(), candidatoCadastrado.getEdicao().getIdEdicao());
        Assertions.assertEquals(candidatoCriado.getEdicao().getNome().toLowerCase(), candidatoCadastrado.getEdicao().getNome().toLowerCase());
        Assertions.assertEquals(candidatoCriado.getFormulario(), candidatoCadastrado.getFormulario().getIdFormulario());
   }


    @Test
    @DisplayName("Cenário 2: Deve retornar 400 quando tenta cadastrar candidato com nome nulo")
    public void testCadastrarCandidatoSemNome() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComNomeNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);


        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("nome: Campo nome não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 400 quando tenta cadastrar candidato com nome em branco")
    public void testCadastrarCandidatoComNomeEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComNomeEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equals("nome: Campo nome não pode ser branco ou nulo.")
                || erroCadastroCandidato.getErrors().get(0).equals("nome: O nome deve ter de 3 a 255 caracteres"));
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento no futuro")
    public void testCadastrarCandidatoComDataNascimentoNoFuturo() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComDataNascimentoNoFuturo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: A data de nascimento deve ser uma data no passado", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 400 quando tenta cadastrar candidato com menos de 16 anos")
    public void testCadastrarCandidatoComMenosDeDezesseisAnos() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComMenosDeDezesseisAnos(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Idade menor que 16 anos.", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 6: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento nula")
    public void testCadastrarCandidatoComDataDeNascimentoNula() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComDataDeNascimentoNula(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: Campo dataNascimento não pode nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 7: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento em branco")
    public void testCadastrarCandidatoComDataDeNascimentoEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComDataDeNascimentoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: Campo dataNascimento não pode nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 8: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento inválida")
    public void testCadastrarCandidatoComDataDeNascimentoInvalida() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComDataDeNascimentoInvalida(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithoutArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Campo dataNascimento com valor inválido.", erroCadastroCandidato.getErrors());
    }

    @Test
    @DisplayName("Cenário 9: Deve retornar 400 quando tenta cadastrar candidato com e-mail nulo")
    public void testCadastrarCandidatoComEmailNulo() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEmailNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("email: Campo email não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 10: Deve retornar 400 quando tenta cadastrar candidato com e-mail em branco")
    public void testCadastrarCandidatoComEmailEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEmailEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("email: Campo email não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 11: Deve retornar 400 quando tenta cadastrar candidato com e-mail sem domínio")
    public void testCadastrarCandidatoComEmailSemDominio() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEmailSemDominio(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).toLowerCase().equals("email: deve ser um endereço de e-mail bem formado")
                || erroCadastroCandidato.getErrors().get(0).toLowerCase().equals("email: must be a well-formed email address"));
    }

    @Test
    @DisplayName("Cenário 12: Deve retornar 400 quando tenta cadastrar candidato com e-mail inválido")
    public void testCadastrarCandidatoComEmailInvalido() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEmailInvalido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).toLowerCase().equals("email: deve ser um endereço de e-mail bem formado")
                || erroCadastroCandidato.getErrors().get(0).toLowerCase().equals("email: must be a well-formed email address"));
    }

    @Test
    @DisplayName("Cenário 13: Deve retornar 400 quando tenta cadastrar candidato com e-mail já cadastrado na mesma edição")
    public void testCadastrarCandidatoComEmailJaCadastrado() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEmailJaCadastrado(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Email já cadastrado na edição.", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 14: Deve retornar 400 quando tenta cadastrar candidato com telefone nulo")
    public void testCadastrarCandidatoComTelefoneNulo() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComTelefoneNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("telefone: Campo telefone não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 15: Deve retornar 400 quando tenta cadastrar candidato com telefone em branco")
    public void testCadastrarCandidatoComTelefoneEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComTelefoneEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equals("telefone: O nome deve ter de 8 a 30 caracteres")
                || erroCadastroCandidato.getErrors().get(0).equals("telefone: Campo telefone não pode ser branco ou nulo."));
    }

    @Test
    @DisplayName("Cenário 16: Deve retornar 400 quando tenta cadastrar candidato com rg nulo")
    public void testCadastrarCandidatoComRgNulo() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComRgNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("rg: Campo rg não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 17: Deve retornar 400 quando tenta cadastrar candidato com rg em branco")
    public void testCadastrarCandidatoComRgEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComRgEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equals("rg: O rg deve ter de 8 a 30 caracteres")
                    || erroCadastroCandidato.getErrors().get(0).equals("rg: Campo rg não deve ser vazio ou nulo."));
    }

    @Test
    @DisplayName("Cenário 18: Deve retornar 400 quando tenta cadastrar candidato com rg com mais de 13 caracteres")
    public void testCadastrarCandidatoComRgMaiorQueTrintaCaracteres() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComRgMaiorQueTrinta(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("rg: O rg deve ter de 8 a 30 caracteres", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 19: Deve retornar 400 quando tenta cadastrar candidato com cpf nulo")
    public void testCadastrarCandidatoComCpfNulo() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComCpfNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cpf: Campo CPF não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 20: Deve retornar 400 quando tenta cadastrar candidato com cpf em branco")
    public void testCadastrarCandidatoComCpfEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComCpfEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equals("cpf: Campo CPF não pode ser branco ou nulo.")
                || erroCadastroCandidato.getErrors().get(0).equals("cpf: Campo CPF inválido."));
    }

    @Test
    @DisplayName("Cenário 21: Deve retornar 400 quando tenta cadastrar candidato com cpf inválido")
    public void testCadastrarCandidatoComCpfInvalido() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComCpfInvalido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cpf: Campo CPF inválido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 22: Deve retornar 400 quando tenta cadastrar candidato com cpf já cadastrado")
    public void testCadastrarCandidatoComCpfJaCadastrado() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComCpfJaCadastrado(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
    }

    @Test
    @DisplayName("Cenário 23: Deve retornar 400 quando tenta cadastrar candidato com estado nulo")
    public void testCadastrarCandidatoComEstadoNulo() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEstadoNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("estado: Campo estado não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 24: Deve retornar 400 quando tenta cadastrar candidato com estado em branco")
    public void testCadastrarCandidatoComEstadoEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEstadoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("estado: Campo estado não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 25: Deve retornar 400 quando tenta cadastrar candidato com cidade nula")
    public void testCadastrarCandidatoComCidadeNula() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComCidadeNula(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cidade: Campo cidade não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 26: Deve retornar 400 quando tenta cadastrar candidato com cidade em branco")
    public void testCadastrarCandidatoComCidadeEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComCidadeEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cidade: Campo cidade não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 27: Deve retornar 400 quando tenta cadastrar candidato com pcd nulo")
    public void testCadastrarCandidatoComPcdNulo() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComPcdNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("pcd: Campo pcd não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 28: Deve retornar 400 quando tenta cadastrar candidato com pcd em branco")
    public void testCadastrarCandidatoComPcdEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComPcdEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("pcd: Campo pcd não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 29: Deve retornar 400 quando tenta cadastrar candidato com ativo nulo")
    public void testCadastrarCandidatoComAtivoNulo() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComAtivoNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("ativo: Campo ativo não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 30: Deve retornar 400 quando tenta cadastrar candidato com ativo em branco")
    public void testCadastrarCandidatoComAtivoEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComAtivoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithoutArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Campo ativo com valor inválido.", erroCadastroCandidato.getErrors());
    }

    @Test
    @DisplayName("Cenário 31: Deve retornar 400 quando tenta cadastrar candidato com lista nula de linguagem")
    public void testCadastrarCandidatoComListaNulaDeLinguagem() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComListaNulaDeLinguagem(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).toLowerCase().equals("linguagens: não deve ser nulo")
                || erroCadastroCandidato.getErrors().get(0).toLowerCase().equals("linguagens: must not be null"));
    }

    @Test
    @DisplayName("Cenário 32: Deve retornar 400 quando tenta cadastrar candidato com lista de linguagem em branco")
    public void testCadastrarCandidatoComListaDeLinguagemEmBranco() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComListaDeLinguagemEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(404, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Linguagem  não cadastrada!", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 33: Deve retornar 400 quando tenta cadastrar candidato com lista de linguagem não cadastrada")
    public void testCadastrarCandidatoComListaDeLinguagemNaoCadastrada() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComListaDeLinguagemNaoCadastrada(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(404, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Linguagem linguagemNaoCadastrada não cadastrada!", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 34: Deve retornar 400 quando tenta cadastrar candidato com edicão nula")
    public void testCadastrarCandidatoComEdicaoNula() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEdicaoNula(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("edicao: Campo edição não pode ser nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 35: Deve retornar 400 quando tenta cadastrar candidato com edicão não existente")
    public void testCadastrarCandidatoComEdicaoNaoExistente() {

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

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComEdicaoNaoExistente(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Edição não encontrada!", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 36: Deve retornar 400 quando tenta cadastrar candidato com id formulário nulo")
    public void testCadastrarCandidatoComIdFormularioNulo() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormulario(listaDeNomeDeTrilhas.get(0));

        EdicaoModel edicaoCriada = edicaoService.criarEdicao();
        LinguagemModel linguagemCriada = linguagemService.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComIdFormularioNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).toLowerCase().equals("formulario: não deve ser nulo")
                || erroCadastroCandidato.getErrors().get(0).toLowerCase().equals("formulario: must not be null"));
    }

    @Test
    @DisplayName("Cenário 37: Deve retornar 400 quando tenta cadastrar candidato com id formulário não cadastrado")
    public void testCadastrarCandidatoComIdFormularioNaoCadastrado() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormulario(listaDeNomeDeTrilhas.get(0));

        EdicaoModel edicaoCriada = edicaoService.criarEdicao();
        LinguagemModel linguagemCriada = linguagemService.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoComIdFormularioNaoCadastrado(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoService.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract()
                    .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(404, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroCadastroCandidato.getMessage());
    }
}
