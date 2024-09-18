package br.com.dbccompany.vemser.tests.formulario;

import client.formulario.FormularioClient;
import models.formulario.JSONListaFormularioResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de formulários")
class ListarFormulariosTest{

    private static final String PATH_SCHEMA_LISTAR_FORMULARIOS = "schemas/formulario/listar_formularios.json";
    FormularioClient formularioClient = new FormularioClient();

    @Tag("Regression")
    @Test
    @DisplayName("Cenário 1: Validar contrato listar formulários ordenados")
    void testListarFormulariosComSucesso() {
        JSONListaFormularioResponse listaFormularioResponse = formularioClient.listarTodosOsFormularios()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(JSONListaFormularioResponse.class);

        Integer idFormulario1 = listaFormularioResponse.getElementos().get(0).getIdFormulario();
        Integer idFormulario2 = listaFormularioResponse.getElementos().get(1).getIdFormulario();

        Assertions.assertTrue(idFormulario2 > idFormulario1);
    }
}
