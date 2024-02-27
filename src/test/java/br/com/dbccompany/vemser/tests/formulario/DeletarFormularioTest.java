package br.com.dbccompany.vemser.tests.formulario;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.FormularioDataFactory;
import models.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.FormularioClient;
import client.TrilhaClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de remoção de formulário")
public class DeletarFormularioTest extends BaseTest {

    private static TrilhaClient trilhaClient = new TrilhaClient();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static FormularioClient formularioClient = new FormularioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar um formulário com sucesso")
    public void testDeletarFormularioComSucesso() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        var response = formularioClient.deletarFormulario(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        JSONFailureResponseWithoutArrayModel erroDelecaoFormulario = formularioClient.deletarFormulario(formularioCriado.getIdFormulario())
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
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        var response = formularioClient.deletarFormularioSemAutenticacao(formularioCriado.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
