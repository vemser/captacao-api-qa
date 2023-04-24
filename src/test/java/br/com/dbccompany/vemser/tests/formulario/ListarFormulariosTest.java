package br.com.dbccompany.vemser.tests.formulario;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import models.formulario.JSONListaFormularioResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.FormularioService;

@Epic("Listar formulários")
@Feature("Listar formulários")
public class ListarFormulariosTest extends BaseTest {

    FormularioService formularioService = new FormularioService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 e listar formulários ordenados")
    public void testListarFormulariosComSucesso() {

        JSONListaFormularioResponse listaFormularioResponse = formularioService.listarTodosOsFormularios()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(JSONListaFormularioResponse.class);

        Integer idFormulario1 = listaFormularioResponse.getElementos().get(0).getIdFormulario();
        Integer idFormulario2 = listaFormularioResponse.getElementos().get(1).getIdFormulario();

        Assertions.assertTrue(idFormulario2 > idFormulario1);
    }
}
