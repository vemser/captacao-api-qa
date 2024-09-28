package br.com.dbccompany.vemser.tests.formulario;

import client.FormularioClient;
import client.TrilhaClient;
import factory.FormularioDataFactory;
import models.failure.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de remoção de formulário")
class DeletarFormularioTest {

    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar um formulário com sucesso")
    @Tag("Regression")
    void testDeletarFormularioComSucesso() {

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

        formularioClient.deletarFormulario(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        JSONFailureResponseWithoutArrayModel erroDelecaoFormulario = formularioClient.deletarFormulario(formularioCriado.getIdFormulario())
                .then()
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(404, erroDelecaoFormulario.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroDelecaoFormulario.getMessage());
    }
}
