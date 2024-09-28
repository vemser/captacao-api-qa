package br.com.dbccompany.vemser.tests.formulario;

import client.FormularioClient;
import client.TrilhaClient;
import factory.FormularioDataFactory;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Endpoint de atualização do formulário")
class AtualizarFormularioTest {

    private static final FormularioClient formularioClient = new FormularioClient();
    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final String PATH_SCHEMA_PUT_FORMULARIO = "schemas/formulario/put_formulario.json";
    private static final String ERRO_MATRICULADO = "O status de matrícula deve ser o mesmo.";
    private static final String ERRO_TURNO = "O turno deve ser o mesmo.";
    private static final String ERRO_INSTITUICAO = "A instituição deve ter sido atualizada.";
    private static final String ERRO_GITHUB = "O GitHub deve ser o mesmo.";
    private static final String ERRO_LINKEDIN = "O LinkedIn deve ser o mesmo.";
    private static final String ERRO_ID_FORMULARIO = "ID do formulário deve ser o mesmo.";

    private static FormularioCriacaoResponseModel formularioCriado;
    private static FormularioCriacaoModel formulario;
    private static final List<String> listaDeNomeDeTrilhas = new ArrayList<>();

    @BeforeAll
    public static void setUp() {

        List<TrilhaModel> trilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(trilhas.get(0).getNome());
    }

    @AfterEach
    public void tearDown() {
        if (formularioCriado != null) {
            formularioClient.deletarFormulario(formularioCriado.getIdFormulario());
        }
    }

    @Test
    @Tag("Contract")
    @DisplayName("Validação de contrato ao atualizar formulário")
    void testValidarContratoAtualizarFormulario() {
        formularioCriado = criarFormulario();
        FormularioCriacaoModel formularioAtualizado = FormularioDataFactory.formularioComInstituicaoAtualizada(formulario);

        formularioClient.atualizaFormularioContrato(formularioCriado.getIdFormulario(), formularioAtualizado)
                .then()
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_PUT_FORMULARIO));
    }

    @Test
    @Tag("Regression")
    @DisplayName("Deve retornar 200 ao atualizar formulário com sucesso")
    void testAtualizarFormularioComSucesso() {

        FormularioCriacaoResponseModel formularioNovo = criarFormulario();

        FormularioCriacaoModel formularioAtualizadoModel = FormularioDataFactory.formularioComInstituicaoAtualizada(formulario);
        FormularioCriacaoResponseModel formularioAtualizado = formularioClient.atualizaFormulario(
                formularioNovo.getIdFormulario(), formularioAtualizadoModel);

        assertAll("Validando dados atualizados do formulário",
                () -> Assertions.assertEquals(formularioNovo.getIdFormulario(), formularioAtualizado.getIdFormulario(), ERRO_ID_FORMULARIO),
                () -> Assertions.assertEquals(formularioNovo.getMatriculado(), formularioAtualizado.getMatriculado(), ERRO_MATRICULADO),
                () -> Assertions.assertEquals(formularioNovo.getTurno(), formularioAtualizado.getTurno(), ERRO_TURNO),
                () -> Assertions.assertEquals(formularioAtualizadoModel.getInstituicao(), formularioAtualizado.getInstituicao(), ERRO_INSTITUICAO),
                () -> Assertions.assertEquals(formularioNovo.getGithub(), formularioAtualizado.getGithub(), ERRO_GITHUB),
                () -> Assertions.assertEquals(formularioNovo.getLinkedin(), formularioAtualizado.getLinkedin(), ERRO_LINKEDIN)
        );

    }

    @Test
    @Tag("Regression")
    @DisplayName("Deve retornar 200 ao realizar upload de comprovante de matrícula com sucesso")
    void testUploadComprovanteMatriculaComSucesso() {

        formularioCriado = criarFormulario();

        formularioClient.incluiComprovanteMatriculaComValidacao(formularioCriado.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_OK);
        
    }

    private static FormularioCriacaoResponseModel criarFormulario() {
        formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);
        return formularioClient.criarFormularioComFormularioEntity(formulario);
    }
}
