package br.com.dbccompany.vemser.tests.formulario;

import client.FormularioClient;
import client.TrilhaClient;
import factory.FormularioDataFactory;
import models.failure.JSONFailureResponseWithArrayModel;
import models.failure.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.config.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Endpoint de cadastro de formulário")
class CadastrarFormularioTest{
    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final List<String> listaDeNomeDeTrilhas = new ArrayList<>();

    private static final String PATH_SCHEMA_POST_FORMULARIO = "schemas/formulario/post_formulario.json";
    private static final String ERRO_NAO_MATRICULADO = "Precisa estar matriculado!";
    private static final String ERRO_TRILHA_NAO_ENCONTRADA = "Trilha não encontrada!";
    private static final String ERRO_INSTITUICAO_NULA = "instituicao: O campo Instituição não deve ser vazio ou nulo.";
    private static final String ERRO_RESPOSTA_VAZIA = "resposta: O campo Resposta não deve ser vazio.";

    public static final String TRILHA_INVALIDA = "teste#-01";

    private static final Map<Object, Object> listaBooleana = Map.of(
            true, "T",
            false, "F",
            "T", true,
            "F", false
    );

    @BeforeAll
    public static void setUp() {

        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel[].class))
                        .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

    }

    @Test
    @Tag("Contract")
    @DisplayName("Cenário 1: Validação de contrato de criar formulario")
    public void testValidarContratoCriacaoFormulario() {

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        formularioClient.criarFormularioContrato(formulario)
                .then()
                    .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_POST_FORMULARIO))
        ;

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 2: Cadastrar formulário com sucesso")
    void testCadastrarFormularioComSucesso() {

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        Assertions.assertAll(
                () -> assertEquals(listaBooleana.get(formulario.getMatriculadoBoolean()), formularioCriado.getMatriculado()),
                () -> assertEquals(formulario.getCurso(), formularioCriado.getCurso()),
                () -> assertEquals(formulario.getTurno(), formularioCriado.getTurno()),
                () -> assertEquals(formulario.getInstituicao(), formularioCriado.getInstituicao()),
                () -> assertEquals(formulario.getGithub(), formularioCriado.getGithub()),
                () -> assertEquals(formulario.getLinkedin(), formularioCriado.getLinkedin()),
                () -> assertEquals(listaBooleana.get(formulario.getDesafiosBoolean()), formularioCriado.getDesafios()),
                () -> assertEquals(listaBooleana.get(formulario.getProblemaBoolean()), formularioCriado.getProblema()),
                () -> assertEquals(listaBooleana.get(formulario.getReconhecimentoBoolean()), formularioCriado.getReconhecimento()),
                () -> assertEquals(listaBooleana.get(formulario.getAltruismoBoolean()), formularioCriado.getAltruismo()),
                () -> assertEquals(formulario.getResposta(), formularioCriado.getResposta()),
                () -> assertEquals(listaBooleana.get(formulario.getLgpdBoolean()), formularioCriado.getLgpd()),
                () -> assertEquals(listaBooleana.get(formulario.getProvaBoolean()), formularioCriado.getProva()),
                () -> assertEquals(formulario.getIngles(), formularioCriado.getIngles()),
                () -> assertEquals(formulario.getEspanhol(), formularioCriado.getEspanhol()),
                () -> assertEquals(formulario.getNeurodiversidade(), formularioCriado.getNeurodiversidade()),
                () -> assertEquals(listaBooleana.get(formulario.getEfetivacaoBoolean()), formularioCriado.getEfetivacao()),
                () -> assertEquals(formulario.getOrientacao(), formularioCriado.getOrientacao()),
                () -> assertArrayEquals(formulario.getTrilhas().toArray(), Tools.listaTrilhaParaListaString(formularioCriado.getTrilhas()).toArray()),
                () -> assertEquals(formulario.getImportancia(), formularioCriado.getImportancia())
        );

        formularioClient.deletarFormulario(formularioCriado.getIdFormulario());

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 3: Tentar cadastrar formulário sem estar matriculado em um curso")
    void testTentarCadastrarFormularioNaoMatriculado() {

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioNaoMatriculado(listaDeNomeDeTrilhas);

        JSONFailureResponseWithoutArrayModel erroNaoMatriculado = formularioClient.criarFormularioDadoInvalido(formulario);

        assertEquals(erroNaoMatriculado.getMessage(), ERRO_NAO_MATRICULADO);
        assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 4: Tentar cadastrar formulário em trilha inválida")
    void testTentarCadastrarFormularioTrilhaInvalida() {

        List<String> listaTrilhaInvalida = new ArrayList<>();
        listaTrilhaInvalida.add(TRILHA_INVALIDA);

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaTrilhaInvalida);

        JSONFailureResponseWithoutArrayModel erroNaoMatriculado = formularioClient.criarFormularioDadoInvalido(formulario);

        assertEquals(erroNaoMatriculado.getMessage(), ERRO_TRILHA_NAO_ENCONTRADA);
        assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 5: Tentar cadastrar formulário com campo instituição nulo")
    void testTentarCadastrarFormularioInstituicaoNula() {

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioInstituicaoNula(listaDeNomeDeTrilhas);

        JSONFailureResponseWithArrayModel erroNaoMatriculado = formularioClient.criarFormularioInstituicaoNula(formulario);

        assertEquals(erroNaoMatriculado.getErrors().get(0), ERRO_INSTITUICAO_NULA);
        assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 6: Tentar cadastrar formulário com campo resposta vazio")
    void testTentarCadastrarFormularioRespostaVazia() {

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioRespostaVazia(listaDeNomeDeTrilhas);

        JSONFailureResponseWithArrayModel erroNaoMatriculado = formularioClient.criarFormularioRespostaVazia(formulario);

        assertEquals(erroNaoMatriculado.getErrors().get(0), ERRO_RESPOSTA_VAZIA);
        assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);

    }
}
