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

@DisplayName("Endpoint de atualização de configuração do PC do candidato")
class AtualizarConfigPcFormularioTest {

    private static final FormularioClient formularioClient = new FormularioClient();
    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final List<String> listaDeNomeDeTrilhas = new ArrayList<>();
    private static FormularioCriacaoResponseModel formularioCriado = new FormularioCriacaoResponseModel();
    private static FormularioCriacaoModel formulario = new FormularioCriacaoModel();
    public static final String ERRO_BUSCAR_O_FORMULÁRIO = "Erro ao buscar o formulário.";
    public static final Integer ID_FORMULARIO_NAO_EXISTENTE = 90000;

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
    @DisplayName("Cenário 1: Deve retornar 200 ao adicionar imagem com configuração do pc com sucesso")
    @Tag("Regression")
    void testEnviarInfoConfigPcComSucesso() {

        formularioCriado = criarFormulario();

        formularioClient.incluiConfigPcEmFormularioComValidacao(formularioCriado.getIdFormulario())
                .then()
                    .statusCode(HttpStatus.SC_OK);

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 ao adicionar imagem com configuração do pc em formulário não existente")
    @Tag("Regression")
    void testEnviarInfoConfigPcParaFormularioNaoExistente() {

        formularioCriado = criarFormulario();

        JSONFailureResponseWithoutArrayModel erroEnvioCurriculo = formularioClient.incluiConfigPcEmFormularioSemValidacao(ID_FORMULARIO_NAO_EXISTENTE)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract()
                    .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, erroEnvioCurriculo.getStatus());
        Assertions.assertEquals(ERRO_BUSCAR_O_FORMULÁRIO, erroEnvioCurriculo.getMessage());
    }

    private static FormularioCriacaoResponseModel criarFormulario() {
        formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);
        return formularioClient.criarFormularioComFormularioEntity(formulario);
    }
}
