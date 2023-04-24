package br.com.dbccompany.vemser.tests.formulario;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.FormularioDataFactory;
import io.qameta.allure.Feature;
import models.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.formulario.JSONListaFormularioResponse;
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

@Feature("Atualizar configuração do pc no formulário")
public class AtualizarConfigPcFormularioTest extends BaseTest {

    private static TrilhaService trilhaService = new TrilhaService();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static FormularioService formularioService = new FormularioService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao adicionar imagem com configuração do pc com sucesso")
    public void testEnviarInfoConfigPcComSucesso() {

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

        formularioService.incluiConfigPcEmFormularioSemValidacao(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 ao adicionar imagem com configuração do pc em formulário não existente")
    public void testEnviarInfoConfigPcParaFormularioNaoExistente() {

        Integer idUltimoFormulario = formularioService.listarNumDeFormulariosOrdemDecrescente(1)
                .then()
                    .extract()
                    .as(JSONListaFormularioResponse.class)
                    .getElementos()
                    .get(0)
                    .getIdFormulario();

        Integer idFormularioNaoExistente = idUltimoFormulario + 1000;

        JSONFailureResponseWithoutArrayModel erroEnvioCurriculo = formularioService.incluiConfigPcEmFormularioSemValidacao(idFormularioNaoExistente)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(404, erroEnvioCurriculo.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroEnvioCurriculo.getMessage());
    }
}
