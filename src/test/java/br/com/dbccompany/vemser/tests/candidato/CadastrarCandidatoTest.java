package br.com.dbccompany.vemser.tests.candidato;

import client.CandidatoClient;
import client.EdicaoClient;
import client.FormularioClient;
import factory.CandidatoDataFactory;
import factory.EdicaoDataFactory;
import factory.FormularioDataFactory;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import models.failure.JSONFailureResponseWithArrayModel;
import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.candidato.CandidatoModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de cadastro de candidato")
class CadastrarCandidatoTest{

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
	private static EdicaoModel edicaoCriada;
    FormularioCriacaoResponseModel formularioCriado;

    @BeforeEach
    void setup() {
        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        listaDeNomeDeTrilhas.add("FRONTEND");
        formularioCriado = CandidatoDataFactory.criarFormularioValido(listaDeNomeDeTrilhas, formularioClient);
        edicaoCriada = CandidatoDataFactory.criarEdicaoValida(edicaoClient);
    }

    @AfterEach
    void tearDown() {
     if (edicaoCriada != null) {
            edicaoClient.deletarEdicao(edicaoCriada.getIdEdicao());
     }
    }

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 e cadastrar candidato com sucesso")
    @Tag("Regression")
    void testCadastrarCandidatoComSucesso() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.criarCandidatoValido(edicaoCriada, formularioCriado.getIdFormulario(), "java", formularioCriado);

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
                .as(CandidatoModel.class);

        CandidatoDataFactory.deletarCandidato(edicaoCriada, candidatoClient, candidatoCadastrado, edicaoClient);

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
    @DisplayName("Cenário 2: Deve validar o contrato de cadastro de candidatos no sistema")
    @Tag("Contract")
    void testValidarContratoCadastrarCandidatos() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.criarCandidatoValido(edicaoCriada, formularioCriado.getIdFormulario(), "java", formularioCriado);

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body(matchesJsonSchemaInClasspath("schemas/candidato/CadastrarCandidato.json"))
            .extract()
                .as(CandidatoModel.class);

        CandidatoDataFactory.deletarCandidato(edicaoCriada, candidatoClient, candidatoCadastrado, edicaoClient);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 400 quando tenta cadastrar candidato com nome nulo")
    @Tag("Regression")
    void testCadastrarCandidatoSemNome() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComNomeNulo(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("nome: must not be null", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 quando tenta cadastrar candidato com nome em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComNomeEmBranco() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComNomeEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

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
    @DisplayName("Cenário 5: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento no futuro")
    @Tag("Regression")
    void testCadastrarCandidatoComDataNascimentoNoFuturo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComDataNascimentoNoFuturo(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: A data não pode ser no futuro", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 6: Deve retornar 400 quando tenta cadastrar candidato com menos de 16 anos")
    @Tag("Regression")
    void testCadastrarCandidatoComMenosDeDezesseisAnos() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComMenosDeDezesseisAnos(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("O candidato deve ter no mínimo 16 anos.", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 7: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento nula")
    @Tag("Regression")
    void testCadastrarCandidatoComDataDeNascimentoNula() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComDataDeNascimentoNula(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: must not be null", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 8: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComDataDeNascimentoEmBranco() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComDataDeNascimentoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("dataNascimento: must not be null", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 9: Deve retornar 400 quando tenta cadastrar candidato com data de nascimento inválida")
    @Tag("Regression")
    void testCadastrarCandidatoComDataDeNascimentoInvalida() {
        RestAssured.defaultParser = Parser.JSON;

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComDataDeNascimentoInvalida(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 10: Deve retornar 400 quando tenta cadastrar candidato com e-mail nulo")
    @Tag("Regression")
    void testCadastrarCandidatoComEmailNulo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailNulo(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("email: O email deve ser preenchido."));
    }

    @Test
    @DisplayName("Cenário 11: Deve retornar 400 quando tenta cadastrar candidato com e-mail sem domínio")
    @Tag("Regression")
    void testCadastrarCandidatoComEmailSemDominio() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailSemDominio(edicaoCriada, formularioCriado.getIdFormulario(), "java");

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
    @Tag("Regression")
    void testCadastrarCandidatoComEmailInvalido() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailInvalido(edicaoCriada, formularioCriado.getIdFormulario(), "java");

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
    @Tag("Regression")
    void testCadastrarCandidatoComEmailJaCadastrado() {

        CandidatoCriacaoResponseModel candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailJaCadastrado(candidatoCadastrado);
        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

		Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals(erroCadastroCandidato.getMessage(), "Candidato com este e-mail já cadastrado para essa edição.");

		candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 14: Deve retornar 400 quando tenta cadastrar candidato com e-mail em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComEmailEmBranco() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEmailEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        List<String> erros = erroCadastroCandidato.getErrors();

        String expected = "email: O email deve ser preenchido.";
        String actual = erros.get(0);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Cenário 15: Deve retornar 400 quando tenta cadastrar candidato com telefone nulo")
    @Tag("Regression")
    void testCadastrarCandidatoComTelefoneNulo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComTelefoneNulo(edicaoCriada, formularioCriado.getIdFormulario(),"java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("telefone: O telefone deve ser preenchido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 16: Deve retornar 400 quando tenta cadastrar candidato com telefone em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComTelefoneEmBranco() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComTelefoneEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertTrue(erroCadastroCandidato.getErrors().get(0).equals("telefone: O nome deve ter de 8 a 30 caracteres")
            || erroCadastroCandidato.getErrors().get(0).equals("telefone: O telefone deve ser preenchido."));
    }

    @Test
    @DisplayName("Cenário 17: Deve retornar 400 quando tenta cadastrar candidato com rg nulo")
    @Tag("Regression")
    void testCadastrarCandidatoComRgNulo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComRgNulo(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("rg: O rg deve ser preenchido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 18: Deve retornar 400 quando tenta cadastrar candidato com rg em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComRgEmBranco() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComRgEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
		Assertions.assertTrue(
				erroCadastroCandidato.getErrors().get(0).equals("rg: O rg deve ter de 5 a 11 caracteres") ||
						erroCadastroCandidato.getErrors().get(0).equals("rg: O rg deve ser preenchido."),
				"A mensagem de erro não corresponde a nenhuma das mensagens esperadas."
		);

	}

    @Test
    @DisplayName("Cenário 19: Deve retornar 400 quando tenta cadastrar candidato com rg com mais de 13 caracteres")
    @Tag("Regression")
    void testCadastrarCandidatoComRgMaiorQueTrintaCaracteres() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComRgMaiorQueTrinta(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("rg: O rg deve ter de 5 a 11 caracteres", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 20: Deve retornar 400 quando tenta cadastrar candidato com cpf nulo")
    @Tag("Regression")
    void testCadastrarCandidatoComCpfNulo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCpfNulo(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cpf: O cpf deve ser preenchido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 21: Deve retornar 400 quando tenta cadastrar candidato com cpf em branco")
    @Tag("Functional")
    void testCadastrarCandidatoComCpfEmBranco() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCpfEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
		Assertions.assertTrue(
				erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("cpf: O cpf deve ser preenchido.") ||
				erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("cpf: invalid Brazilian individual taxpayer registry number (CPF)")
		);
    }

    @Test
    @DisplayName("Cenário 22: Deve retornar 400 quando tenta cadastrar candidato com cpf inválido")
    @Tag("Regression")
    void testCadastrarCandidatoComCpfInvalido() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCpfInvalido(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cpf: invalid Brazilian individual taxpayer registry number (CPF)", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 23: Deve retornar 400 quando tenta cadastrar candidato com cpf já cadastrado")
    @Tag("Functional")
    void testCadastrarCandidatoComCpfJaCadastrado() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCpfJaCadastrado();

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
    }

    @Test
    @DisplayName("Cenário 24: Deve retornar 400 quando tenta cadastrar candidato com estado nulo")
    @Tag("Regression")
    void testCadastrarCandidatoComEstadoNulo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEstadoNulo(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("estado: O estado deve ser preenchido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 25: Deve retornar 400 quando tenta cadastrar candidato com estado em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComEstadoEmBranco() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEstadoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("estado: O estado deve ser preenchido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 26: Deve retornar 400 quando tenta cadastrar candidato com cidade nula")
    @Tag("Regression")
    void testCadastrarCandidatoComCidadeNula() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCidadeNula(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("cidade: A cidade deve ser preenchido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 27: Deve retornar 400 quando tenta cadastrar candidato com cidade em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComCidadeEmBranco() {

		CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComCidadeEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

		JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				.extract()
				.as(JSONFailureResponseWithArrayModel.class);

		Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
		Assertions.assertTrue(
				erroCadastroCandidato.getErrors().get(0).equals("cidade: O nome deve ter de 3 a 30 caracteres") ||
						erroCadastroCandidato.getErrors().get(0).equalsIgnoreCase("cidade: A cidade deve ser preenchido."
						));
	}

    @Test
    @DisplayName("Cenário 28: Deve retornar 400 quando tenta cadastrar candidato com pcd nulo")
    @Tag("Regression")
    void testCadastrarCandidatoComPcdNulo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComPcdNulo(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("pcd: O campo PCD deve ser preenchido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 29: Deve retornar 400 quando tenta cadastrar candidato com pcd em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComPcdEmBranco() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComPcdEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("pcd: O campo PCD deve ser preenchido.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 30: Deve retornar 400 quando tenta cadastrar candidato com ativo nulo")
    @Tag("Regression")
    void testCadastrarCandidatoComAtivoNulo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComAtivoNulo(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("ativo: O campo não pode ser nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 31: Deve retornar 400 quando tenta cadastrar candidato com ativo em branco")
    @Tag("Regression")
    void testCadastrarCandidatoComAtivoEmBranco() {
        RestAssured.defaultParser = Parser.JSON;

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComAtivoEmBranco(edicaoCriada, formularioCriado.getIdFormulario(), "java");

        candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 32: Deve retornar 400 quando tenta cadastrar candidato com lista nula de linguagem")
    @Tag("Regression")
    void testCadastrarCandidatoComListaNulaDeLinguagem() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComListaNulaDeLinguagem(edicaoCriada, formularioCriado.getIdFormulario(), null);

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
    }

    @Test
    @DisplayName("Cenário 33: Deve retornar 400 quando tenta cadastrar candidato com edicão nula")
    @Tag("Regression")
    void testCadastrarCandidatoComEdicaoNula() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEdicaoNula(null, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(400, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("edicao: O campo não pode ser nulo.", erroCadastroCandidato.getErrors().get(0));
    }

    @Test
    @DisplayName("Cenário 34: Deve retornar 400 quando tenta cadastrar candidato com edicão não existente")
    @Tag("Regression")
    void testCadastrarCandidatoComEdicaoNaoExistente() {

        EdicaoModel edicaoNaoExistente = EdicaoDataFactory.edicaoValida();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComEdicaoNaoExistente(edicaoNaoExistente, formularioCriado.getIdFormulario(), "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals("Edição não encontrada!", erroCadastroCandidato.getMessage());
    }

    @Test
    @DisplayName("Cenário 35: Deve retornar 400 quando tenta cadastrar candidato com id formulário nulo")
    @Tag("Regression")
    void testCadastrarCandidatoComIdFormularioNulo() {

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComIdFormularioNulo(edicaoCriada, null, "java");

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
    @DisplayName("Cenário 36: Deve retornar 404 quando tenta cadastrar candidato com id formulário não cadastrado")
    @Tag("Regression")
    void testCadastrarCandidatoComIdFormularioNaoCadastrado() {

        Integer idFormularioNaoCadastrado = FormularioDataFactory.idFormularioNaoCadastrado();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoComIdFormularioNaoCadastrado(edicaoCriada, idFormularioNaoCadastrado, "java");

        JSONFailureResponseWithArrayModel erroCadastroCandidato = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .extract()
                .as(JSONFailureResponseWithArrayModel.class);

        Assertions.assertEquals(404, erroCadastroCandidato.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroCadastroCandidato.getMessage());
    }
}
