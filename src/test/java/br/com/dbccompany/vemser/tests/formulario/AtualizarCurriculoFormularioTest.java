package br.com.dbccompany.vemser.tests.formulario;

import client.FormularioClient;
import client.TrilhaClient;
import factory.FormularioDataFactory;
import models.failure.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de atualização de currículo do candidato")
class AtualizarCurriculoFormularioTest{

    private static final FormularioClient formularioClient = new FormularioClient();
    private static final TrilhaClient trilhaClient = new TrilhaClient();
    public static final String ERRO_BUSCAR_FORMULARIO = "Erro ao buscar o formulário.";
    private static FormularioCriacaoResponseModel formularioCriado = new FormularioCriacaoResponseModel();
    private static final List<String> listaDeNomeDeTrilhas = new ArrayList<>();
    private static FormularioCriacaoModel formulario = new FormularioCriacaoModel();
    public static final Integer ID_FORMULARIO_NAO_EXISTENTE = 900000;

    @BeforeAll
    public static void setUp() {

        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel[].class))
                    .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

    }

    @AfterEach
    public void tearDown() {
        formularioClient.deletarFormulario(formularioCriado.getIdFormulario());
    }

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao enviar currículo com sucesso")
    @Tag("Regression")
    void testEnviarCurriculoComSucesso() {

        formularioCriado = criarFormulario();

        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_OK);

        formularioClient.deletarFormulario(formularioCriado.getIdFormulario());

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 ao enviar currículo para formulário não existente")
    @Tag("Regression")
    void testEnviarCurriculoParaFormularioNaoExistente() {

        formularioCriado = criarFormulario();

        JSONFailureResponseWithoutArrayModel erroEnvioCurriculo = formularioClient.incluiCurriculoEmFormularioComValidacao(ID_FORMULARIO_NAO_EXISTENTE)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, erroEnvioCurriculo.getStatus());
        Assertions.assertEquals(ERRO_BUSCAR_FORMULARIO, erroEnvioCurriculo.getMessage());

    }

    private static FormularioCriacaoResponseModel criarFormulario() {
        formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);
        return formularioClient.criarFormularioComFormularioEntity(formulario);
    }

}
