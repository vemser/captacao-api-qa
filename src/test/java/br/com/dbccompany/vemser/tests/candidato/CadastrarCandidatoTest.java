package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.linguagem.LinguagemClient;
import client.trilha.TrilhaClient;
import factory.candidato.CandidatoDataFactory;
import factory.formulario.FormularioDataFactory;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de cadastro de candidato")
class CadastrarCandidatoTest{

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final LinguagemClient linguagemClient = new LinguagemClient();


    @Test
    @DisplayName("Cenário 1: Deve retornar 200 e cadastrar candidato com sucesso")
    void testCadastrarCandidatoComSucesso() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoModel.class);

        var deletarCandidato = candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        edicaoClient.deletarEdicao(edicaoCriada.getIdEdicao());



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
    void testCadastrarCandidatoSemNome() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComNomeNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);


        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("nome: Campo nome não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 400 quando tenta cadastrar candidato com nome em branco")
    void testCadastrarCandidatoComNomeEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComNomeEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
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
    void testCadastrarCandidatoComDataNascimentoNoFuturo() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComDataNascimentoNoFuturo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: A data de nascimento deve ser uma data no passado", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 400 quando tenta cadastrar candidato com menos de 16 anos")
    void testCadastrarCandidatoComMenosDeDezesseisAnos() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComMenosDeDezesseisAnos(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Idade menor que 16 anos.", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 6: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento nula")
    void testCadastrarCandidatoComDataDeNascimentoNula() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComDataDeNascimentoNula(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: Campo dataNascimento não pode nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 7: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento em branco")
    void testCadastrarCandidatoComDataDeNascimentoEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComDataDeNascimentoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: Campo dataNascimento não pode nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 8: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento inválida")
    void testCadastrarCandidatoComDataDeNascimentoInvalida() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComDataDeNascimentoInvalida(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithoutArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Campo dataNascimento com valor inválido.", erroCadastroCandidato.getErrors());
    }

    @Test
    @DisplayName("Cenário 9: Deve retornar 400 quando tenta cadastrar candidato com e-mail nulo")
    void testCadastrarCandidatoComEmailNulo() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("email: Campo email não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 10: Deve retornar 400 quando tenta cadastrar candidato com e-mail em branco")
    void testCadastrarCandidatoComEmailEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("email: Campo email não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 11: Deve retornar 400 quando tenta cadastrar candidato com e-mail sem domínio")
    void testCadastrarCandidatoComEmailSemDominio() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailSemDominio(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("email: deve ser um endereço de e-mail bem formado")
                || erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("email: must be a well-formed email address"));
    }

    @Test
    @DisplayName("Cenário 12: Deve retornar 400 quando tenta cadastrar candidato com e-mail inválido")
    void testCadastrarCandidatoComEmailInvalido() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailInvalido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("email: deve ser um endereço de e-mail bem formado")
                || erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("email: must be a well-formed email address"));
    }

    @Test
    @DisplayName("Cenário 13: Deve retornar 400 quando tenta cadastrar candidato com e-mail já cadastrado na mesma edição")
    void testCadastrarCandidatoComEmailJaCadastrado() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailJaCadastrado();

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);
    }

    @Test
    @DisplayName("Cenário 14: Deve retornar 400 quando tenta cadastrar candidato com telefone nulo")
    void testCadastrarCandidatoComTelefoneNulo() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComTelefoneNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("telefone: Campo telefone não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 15: Deve retornar 400 quando tenta cadastrar candidato com telefone em branco")
    void testCadastrarCandidatoComTelefoneEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComTelefoneEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
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
    void testCadastrarCandidatoComRgNulo() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComRgNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("rg: Campo rg não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 17: Deve retornar 400 quando tenta cadastrar candidato com rg em branco")
    void testCadastrarCandidatoComRgEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComRgEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
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
    void testCadastrarCandidatoComRgMaiorQueTrintaCaracteres() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComRgMaiorQueTrinta(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("rg: O rg deve ter de 8 a 30 caracteres", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 19: Deve retornar 400 quando tenta cadastrar candidato com cpf nulo")
    void testCadastrarCandidatoComCpfNulo() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCpfNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cpf: Campo CPF não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 20: Deve retornar 400 quando tenta cadastrar candidato com cpf em branco")
    void testCadastrarCandidatoComCpfEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCpfEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
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
    void testCadastrarCandidatoComCpfInvalido() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCpfInvalido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cpf: Campo CPF inválido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 22: Deve retornar 400 quando tenta cadastrar candidato com cpf já cadastrado")
    void testCadastrarCandidatoComCpfJaCadastrado() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCpfJaCadastrado();

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
    }

    @Test
    @DisplayName("Cenário 23: Deve retornar 400 quando tenta cadastrar candidato com estado nulo")
    void testCadastrarCandidatoComEstadoNulo() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEstadoNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("estado: Campo estado não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 24: Deve retornar 400 quando tenta cadastrar candidato com estado em branco")
    void testCadastrarCandidatoComEstadoEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEstadoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("estado: Campo estado não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 25: Deve retornar 400 quando tenta cadastrar candidato com cidade nula")
    void testCadastrarCandidatoComCidadeNula() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCidadeNula(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cidade: Campo cidade não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 26: Deve retornar 400 quando tenta cadastrar candidato com cidade em branco")
    void testCadastrarCandidatoComCidadeEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCidadeEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cidade: Campo cidade não deve ser vazio ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 27: Deve retornar 400 quando tenta cadastrar candidato com pcd nulo")
    void testCadastrarCandidatoComPcdNulo() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComPcdNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("pcd: Campo pcd não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 28: Deve retornar 400 quando tenta cadastrar candidato com pcd em branco")
    void testCadastrarCandidatoComPcdEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComPcdEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("pcd: Campo pcd não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 29: Deve retornar 400 quando tenta cadastrar candidato com ativo nulo")
    void testCadastrarCandidatoComAtivoNulo() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComAtivoNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("ativo: Campo ativo não pode ser branco ou nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 30: Deve retornar 400 quando tenta cadastrar candidato com ativo em branco")
    void testCadastrarCandidatoComAtivoEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComAtivoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithoutArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Campo ativo com valor inválido.", erroCadastroCandidato.getErrors());
    }

    @Test
    @DisplayName("Cenário 31: Deve retornar 400 quando tenta cadastrar candidato com lista nula de linguagem")
    void testCadastrarCandidatoComListaNulaDeLinguagem() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComListaNulaDeLinguagem(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("linguagens: não deve ser nulo")
                || erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("linguagens: must not be null"));
    }

    @Test
    @DisplayName("Cenário 32: Deve retornar 400 quando tenta cadastrar candidato com lista de linguagem em branco")
    void testCadastrarCandidatoComListaDeLinguagemEmBranco() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComListaDeLinguagemEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(404, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Linguagem  não cadastrada!", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 33: Deve retornar 400 quando tenta cadastrar candidato com lista de linguagem não cadastrada")
    void testCadastrarCandidatoComListaDeLinguagemNaoCadastrada() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComListaDeLinguagemNaoCadastrada(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(404, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Linguagem linguagemNaoCadastrada não cadastrada!", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 34: Deve retornar 400 quando tenta cadastrar candidato com edicão nula")
    void testCadastrarCandidatoComEdicaoNula() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEdicaoNula(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("edicao: Campo edição não pode ser nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 35: Deve retornar 404 quando tenta cadastrar candidato com edicão não existente")
    void testCadastrarCandidatoComEdicaoNaoExistente() {

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

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEdicaoNaoExistente(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals("Edição não encontrada!", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 36: Deve retornar 400 quando tenta cadastrar candidato com id formulário nulo")
    void testCadastrarCandidatoComIdFormularioNulo() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormulario(listaDeNomeDeTrilhas.get(0));

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComIdFormularioNulo(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("formulario: não deve ser nulo")
                || erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("formulario: must not be null"));
    }

    @Test
    @DisplayName("Cenário 37: Deve retornar 400 quando tenta cadastrar candidato com id formulário não cadastrado")
    void testCadastrarCandidatoComIdFormularioNaoCadastrado() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormulario(listaDeNomeDeTrilhas.get(0));

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComIdFormularioNaoCadastrado(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(404, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroCadastroCandidato.getMessage());
    }
}
