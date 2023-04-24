package br.com.dbccompany.vemser.tests.formulario;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.FormularioDataFactory;
import io.qameta.allure.Feature;
import models.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.FormularioService;
import service.TrilhaService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Feature("Deletar formulário")
public class DeletarFormularioTest extends BaseTest {

    private static TrilhaService trilhaService = new TrilhaService();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static FormularioService formularioService = new FormularioService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar um formulário com sucesso")
    public void testDeletarFormularioComSucesso() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormularioComFormularioEntity(formulario);

        var response = formularioService.deletarFormulario(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        JSONFailureResponseWithoutArrayModel erroDelecaoFormulario = formularioService.deletarFormulario(formularioCriado.getIdFormulario())
                .then()
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(404, erroDelecaoFormulario.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroDelecaoFormulario.getMessage());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando tenta deletar formulário sem estar autenticado")
    public void testDeletarFormularioSemAutenticacao() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormularioComFormularioEntity(formulario);

        var response = formularioService.deletarFormularioSemAutenticacao(formularioCriado.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
