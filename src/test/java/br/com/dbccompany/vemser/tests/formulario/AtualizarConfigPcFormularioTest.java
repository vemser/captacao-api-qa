package br.com.dbccompany.vemser.tests.formulario;

import client.FormularioClient;
import factory.FormularioDataFactory;
import models.failure.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Endpoint de atualização de configuração do PC do candidato")
class AtualizarConfigPcFormularioTest {

    private static final FormularioClient formularioClient = new FormularioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao adicionar imagem com configuração do pc com sucesso")
    @Tag("Regression")
    void testEnviarInfoConfigPcComSucesso() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();

        listaDeNomeDeTrilhas.add("QA");

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        formularioClient.incluiConfigPcEmFormularioComValidacao(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_OK);

        formularioClient.deletarFormulario(formularioCriado.getIdFormulario());

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 ao adicionar imagem com configuração do pc em formulário não existente")
    @Tag("Regression")
    void testEnviarInfoConfigPcParaFormularioNaoExistente() {

        Integer idFormularioNaoExistente = 90000;

        JSONFailureResponseWithoutArrayModel erroEnvioCurriculo = formularioClient.incluiConfigPcEmFormularioSemValidacao(idFormularioNaoExistente)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(404, erroEnvioCurriculo.getStatus());
        Assertions.assertEquals("Erro ao buscar o formulário.", erroEnvioCurriculo.getMessage());
    }
}
