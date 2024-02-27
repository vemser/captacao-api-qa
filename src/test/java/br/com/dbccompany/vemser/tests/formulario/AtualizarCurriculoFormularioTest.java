package br.com.dbccompany.vemser.tests.formulario;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.FormularioDataFactory;
import models.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.formulario.JSONListaFormularioResponse;
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

@DisplayName("Endpoint de atualização de currículo do candidato")
public class AtualizarCurriculoFormularioTest extends BaseTest {

    private static FormularioClient formularioClient = new FormularioClient();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static TrilhaClient trilhaClient = new TrilhaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao enviar currículo com sucesso")
    public void testEnviarCurriculoComSucesso() {

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

        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 ao enviar currículo para formulário não existente")
    public void testEnviarCurriculoParaFormularioNaoExistente() {

        Integer idUltimoFormulario = formularioClient.listarNumDeFormulariosOrdemDecrescente(1)
                        .then()
                                .extract()
                                .as(JSONListaFormularioResponse.class)
                                .getElementos()
                                .get(0)
                                .getIdFormulario();

        Integer idFormularioNaoExistente = idUltimoFormulario + 1000;

        JSONFailureResponseWithoutArrayModel erroEnvioCurriculo = formularioClient.incluiCurriculoEmFormularioSemValidacao(idFormularioNaoExistente)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(404, erroEnvioCurriculo.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroEnvioCurriculo.getMessage());
    }
}
