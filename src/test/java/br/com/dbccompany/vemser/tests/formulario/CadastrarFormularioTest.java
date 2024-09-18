package br.com.dbccompany.vemser.tests.formulario;

import client.formulario.FormularioClient;
import client.trilha.TrilhaClient;
import factory.formulario.FormularioDataFactory;
import models.JSONFailureResponseWithArrayModel;
import models.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.config.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@DisplayName("Endpoint de cadastro de formulário")
class CadastrarFormularioTest{
    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();

    private static final Map<Object, Object> listaBooleana = Map.of(
            true, "T",
            false, "F",
            "T", true,
            "F", false
    );


    @Test
    @DisplayName("Cenário 1: Cadastrar formulário com sucesso")
    void testCadastrarFormularioComSucesso() {

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

        Assertions.assertEquals(listaBooleana.get(formulario.getMatriculadoBoolean()), formularioCriado.getMatriculado());
        Assertions.assertEquals(formulario.getCurso(), formularioCriado.getCurso());
        Assertions.assertEquals(formulario.getTurno(), formularioCriado.getTurno());
        Assertions.assertEquals(formulario.getInstituicao(), formularioCriado.getInstituicao());
        Assertions.assertEquals(formulario.getGithub(), formularioCriado.getGithub());
        Assertions.assertEquals(formulario.getLinkedin(), formularioCriado.getLinkedin());
        Assertions.assertEquals(listaBooleana.get(formulario.getDesafiosBoolean()), formularioCriado.getDesafios());
        Assertions.assertEquals(listaBooleana.get(formulario.getProblemaBoolean()), formularioCriado.getProblema());
        Assertions.assertEquals(listaBooleana.get(formulario.getReconhecimentoBoolean()), formularioCriado.getReconhecimento());
        Assertions.assertEquals(listaBooleana.get(formulario.getAltruismoBoolean()), formularioCriado.getAltruismo());
        Assertions.assertEquals(formulario.getResposta(), formularioCriado.getResposta());
        Assertions.assertEquals(listaBooleana.get(formulario.getLgpdBoolean()), formularioCriado.getLgpd());
        Assertions.assertEquals(listaBooleana.get(formulario.getProvaBoolean()), formularioCriado.getProva());
        Assertions.assertEquals(formulario.getIngles(), formularioCriado.getIngles());
        Assertions.assertEquals(formulario.getEspanhol(), formularioCriado.getEspanhol());
        Assertions.assertEquals(formulario.getNeurodiversidade(), formularioCriado.getNeurodiversidade());
        Assertions.assertEquals(listaBooleana.get(formulario.getEfetivacaoBoolean()), formularioCriado.getEfetivacao());
        Assertions.assertEquals(formulario.getOrientacao(), formularioCriado.getOrientacao());
        Assertions.assertArrayEquals(formulario.getTrilhas().toArray(), Tools.listaTrilhaParaListaString(formularioCriado.getTrilhas()).toArray());
        Assertions.assertEquals(formulario.getImportancia(), formularioCriado.getImportancia());
    }

    @Test
    @DisplayName("Cenário 2: Tentar cadastrar formulário sem estar matriculado em um curso")
    void testTentarCadastrarFormularioNaoMatriculado() {
        String MSG_ERRO = "Precisa estar matriculado!";

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel[].class))
                        .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioNaoMatriculado(listaDeNomeDeTrilhas);

        JSONFailureResponseWithoutArrayModel erroNaoMatriculado = formularioClient.criarFormularioDadoInvalido(formulario);

        Assertions.assertEquals(erroNaoMatriculado.getMessage(), MSG_ERRO);
        Assertions.assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 3: Tentar cadastrar formulário sem curso no periodo noturno")
    void testTentarCadastrarFormularioTurnoDiferenteDeNoite() {
        String MSG_ERRO = "Precisa estar matriculado em um curso do turno da noite!";

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel[].class))
                        .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioTurnoInvalido(listaDeNomeDeTrilhas);

        JSONFailureResponseWithoutArrayModel erroNaoMatriculado = formularioClient.criarFormularioDadoInvalido(formulario);

        Assertions.assertEquals(erroNaoMatriculado.getMessage(), MSG_ERRO);
        Assertions.assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 4: Tentar cadastrar formulário em trilha inválida")
    void testTentarCadastrarFormularioTrilhaInvalida() {
        String MSG_ERRO = "Trilha não encontrada!";

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();

        listaDeNomeDeTrilhas.add("teste#-01");

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        JSONFailureResponseWithoutArrayModel erroNaoMatriculado = formularioClient.criarFormularioDadoInvalido(formulario);

        Assertions.assertEquals(erroNaoMatriculado.getMessage(), MSG_ERRO);
        Assertions.assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 5: Tentar cadastrar formulário com campo semestre atual negativo")
    void testTentarCadastrarFormularioSemestreAtualNegativo() {
        String MSG_ERRO = "Semestre atual inválido!";

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel[].class))
                        .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioSemestreNegativo(listaDeNomeDeTrilhas);

        JSONFailureResponseWithoutArrayModel erroNaoMatriculado = formularioClient.criarFormularioDadoInvalido(formulario);

        Assertions.assertEquals(erroNaoMatriculado.getMessage(), MSG_ERRO);
        Assertions.assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 6: Tentar cadastrar formulário com campo quantidade de semestres negativo")
    void testTentarCadastrarFormularioQntSemestresNegativo() {
        String MSG_ERRO = "Quantidade de semestres inválida!";

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel[].class))
                        .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioQntSemestresNegativo(listaDeNomeDeTrilhas);

        JSONFailureResponseWithoutArrayModel erroNaoMatriculado = formularioClient.criarFormularioDadoInvalido(formulario);

        Assertions.assertEquals(erroNaoMatriculado.getMessage(), MSG_ERRO);
        Assertions.assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 7: Tentar cadastrar formulário com campo instituição nulo")
    void testTentarCadastrarFormularioInstituicaoNula() {
        String MSG_ERRO = "instituicao: O campo Instituição não deve ser vazio ou nulo.";

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel[].class))
                        .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioInstituicaoNula(listaDeNomeDeTrilhas);

        JSONFailureResponseWithArrayModel erroNaoMatriculado = formularioClient.criarFormularioInstituicaoNula(formulario);

        Assertions.assertEquals(erroNaoMatriculado.getErrors().get(0), MSG_ERRO);
        Assertions.assertEquals(erroNaoMatriculado.getStatus(), HttpStatus.SC_BAD_REQUEST);
    }
}
