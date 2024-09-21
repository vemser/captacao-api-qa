package br.com.dbccompany.vemser.tests.formulario;

import client.formulario.FormularioClient;
import factory.formulario.FormularioDataFactory;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import models.JSONFailureResponseWithArrayModel;
import models.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.formulario.JSONListaFormularioResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de formulários")
class ListarFormulariosTest{

    private static final String PATH_SCHEMA_LISTAR_FORMULARIOS = "schemas/formulario/listar_formularios.json";
    private static final String TRILHA_VALIDA = "FRONTEND";
    FormularioClient formularioClient = new FormularioClient();


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
        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        listaDeNomeDeTrilhas.add(TRILHA_VALIDA);

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);
        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        formularioClient.recuperarCompMatricula(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                ;

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 5: Buscar Comprovação de matricula sem documento cadastrado")
    void testTentarBuscarComprovacaoMatriculaSemDocumentosCadastrados() {
        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        listaDeNomeDeTrilhas.add(TRILHA_VALIDA);

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);
        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        JSONFailureResponseWithoutArrayModel response = formularioClient.recuperarCompMatricula(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);


        Assertions.assertEquals("Usuário não possui documentos.", response.getMessage());
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatus());
    }
}
