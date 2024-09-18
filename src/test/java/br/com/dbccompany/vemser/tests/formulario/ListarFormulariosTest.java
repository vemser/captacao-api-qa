package br.com.dbccompany.vemser.tests.formulario;

import client.formulario.FormularioClient;
import models.formulario.JSONListaFormularioResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de listagem de formulários")
class ListarFormulariosTest{

    FormularioClient formularioClient = new FormularioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 e listar formulários ordenados")
    @Tag("Regression")
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
