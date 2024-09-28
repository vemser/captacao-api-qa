package br.com.dbccompany.vemser.tests.formulario;

import client.FormularioClient;
import client.TrilhaClient;
import factory.FormularioDataFactory;
import models.failure.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.formulario.JSONListaFormularioResponse;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de formulários")
class ListarFormulariosTest{

    private static final String PATH_SCHEMA_LISTAR_FORMULARIOS = "schemas/formulario/listar_formularios.json";
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final List<String> listaDeNomeDeTrilhas = new ArrayList<>();

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

    @AfterAll
    public static void tearDown() {
    }

    @Test
    @Tag("Contract")
    @DisplayName("Cenário 1: Validar contrato listar formulários")
    void testValidarContratoListarFormularios() {

        formularioClient.listarTodosOsFormularios()
                .then()
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_FORMULARIOS));

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 2: Validar listar formulários ordenados com sucesso")
    void testListarFormulariosComSucesso()  {

        JSONListaFormularioResponse listaFormularioResponse = formularioClient.listarTodosOsFormularios()
                        .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(JSONListaFormularioResponse.class);

        Integer idFormulario1 = listaFormularioResponse.getElementos().get(0).getIdFormulario();
        Integer idFormulario2 = listaFormularioResponse.getElementos().get(1).getIdFormulario();

        Assertions.assertTrue(idFormulario2 > idFormulario1);

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 3: Tentar listar formulários sem autenticação")
    void testTentarListarFormulariosSemAutenticacao() {

        formularioClient.listarFormulariosSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 4: Buscar Comprovação de matricula com sucesso")
    void testTentarBuscarComprovacaoMatriculaComSucessp() {

        FormularioCriacaoResponseModel formularioNovo = criarFormulario();

        formularioClient.incluiComprovanteMatriculaComValidacao(formularioNovo.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_OK);

        formularioClient.recuperarCompMatricula(formularioNovo.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                ;

        formularioClient.deletarFormulario(formularioNovo.getIdFormulario());

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 5: Buscar Comprovação de matricula sem documento cadastrado")
    void testTentarBuscarComprovacaoMatriculaSemDocumentoCadastrado() {

        FormularioCriacaoResponseModel formularioNovo = criarFormulario();

        JSONFailureResponseWithoutArrayModel response = formularioClient.recuperarCompMatricula(formularioNovo.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals("Usuário não possui documentos.", response.getMessage());
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());

        formularioClient.deletarFormulario(formularioNovo.getIdFormulario());

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 6: Buscar print de configurações do PC com sucesso")
    void testTentarBuscarPrintConfigPcComSucesso() {

        FormularioCriacaoResponseModel formularioNovo = criarFormulario();

        formularioClient.incluiConfigPcEmFormularioComValidacao(formularioNovo.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_OK);

        formularioClient.recuperarPrintConfigPc(formularioNovo.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;

        formularioClient.deletarFormulario(formularioNovo.getIdFormulario());

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 7: Buscar Print configuração PC sem configuração cadastrada")
    void testTentarBuscarConfigPCSemConfigCadastrada() {

        FormularioCriacaoResponseModel formularioNovo = criarFormulario();

        JSONFailureResponseWithoutArrayModel response = formularioClient.recuperarPrintConfigPc(formularioNovo.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals("Usuário não possui print das configurações do pc cadastrado.", response.getMessage());
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());

        formularioClient.deletarFormulario(formularioNovo.getIdFormulario());

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 8: Buscar curriculo com sucesso")
    void testTentarBuscarCurriculoComSucessp() {

        FormularioCriacaoResponseModel formularioNovo = criarFormulario();

        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioNovo.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_OK);

        formularioClient.recuperarCurriculo(formularioNovo.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;

        formularioClient.deletarFormulario(formularioNovo.getIdFormulario());

    }


    @Test
    @Tag("Regression")
    @DisplayName("Cenário 9: Buscar curriculo sem possuir documento cadastrado")
    void testTentarBuscarCurriculoSemDocumentoCadastrado() {

        FormularioCriacaoResponseModel formularioNovo = criarFormulario();

        JSONFailureResponseWithoutArrayModel response = formularioClient.recuperarCurriculo(formularioNovo.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals("Usuário não possui currículo cadastrado.", response.getMessage());
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());

        formularioClient.deletarFormulario(formularioNovo.getIdFormulario());

    }

    private static FormularioCriacaoResponseModel criarFormulario() {
        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);
        return formularioClient.criarFormularioComFormularioEntity(formulario);
    }
}
