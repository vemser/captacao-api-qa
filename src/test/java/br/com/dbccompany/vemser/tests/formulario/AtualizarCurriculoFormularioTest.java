package br.com.dbccompany.vemser.tests.formulario;

import client.formulario.FormularioClient;
import factory.formulario.FormularioDataFactory;
import models.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Endpoint de atualização de currículo do candidato")
class AtualizarCurriculoFormularioTest{

    private static final FormularioClient formularioClient = new FormularioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao enviar currículo com sucesso")
    @Tag("Regression")
    void testEnviarCurriculoComSucesso() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();

        listaDeNomeDeTrilhas.add("QA");

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_OK);

        formularioClient.deletarFormulario(formularioCriado.getIdFormulario());

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 ao enviar currículo para formulário não existente")
    @Tag("Regression")
    void testEnviarCurriculoParaFormularioNaoExistente() {

        Integer idFormularioNaoExistente = 900000;

        JSONFailureResponseWithoutArrayModel erroEnvioCurriculo = formularioClient.incluiCurriculoEmFormularioSemValidacao(idFormularioNaoExistente)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(404, erroEnvioCurriculo.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroEnvioCurriculo.getMessage());
    }
}
