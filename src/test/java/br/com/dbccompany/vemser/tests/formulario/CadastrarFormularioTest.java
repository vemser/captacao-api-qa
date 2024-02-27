package br.com.dbccompany.vemser.tests.formulario;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.FormularioDataFactory;
import io.restassured.http.ContentType;
import models.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.EdicaoClient;
import client.FormularioClient;
import client.TrilhaClient;
import utils.Auth;
import utils.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@DisplayName("Endpoint de cadastro de formulário")
public class CadastrarFormularioTest extends BaseTest {

    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static EdicaoClient edicaoClient = new EdicaoClient();
    private static TrilhaClient trilhaClient = new TrilhaClient();
    private static FormularioClient formularioClient = new FormularioClient();

    private static Map<Object, Object> listaBooleana = Map.of(
            true, "T",
            false, "F",
            "T", true,
            "F", false
    );


    @Test
    @DisplayName("Cenário 1: Cadastrar formulário com sucesso")
    public void testCadastrarFormularioComSucesso() {

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
        Assertions.assertEquals(formulario.getEtnia(), formularioCriado.getEtnia());
        Assertions.assertEquals(listaBooleana.get(formulario.getEfetivacaoBoolean()), formularioCriado.getEfetivacao());
        Assertions.assertEquals(formulario.getOrientacao(), formularioCriado.getOrientacao());
        Assertions.assertTrue(Arrays.equals(formulario.getTrilhas().toArray(), Tools.listaTrilhaParaListaString(formularioCriado.getTrilhas()).toArray()));
        Assertions.assertEquals(formulario.getImportancia(), formularioCriado.getImportancia());
    }

    @Test
    @DisplayName("Cenário 2: Cadastrar formulário com matriculado false")
    public void testCadastrarFormularioComMatriculadoFalse() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioComMatriculadoFalse(listaDeNomeDeTrilhas);

        JSONFailureResponseWithoutArrayModel erroCriacaoFormulario =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(formulario)
                .when()
                        .post("/formulario/cadastro")
                .then()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .as(JSONFailureResponseWithoutArrayModel.class);

        Assertions.assertEquals(400, erroCriacaoFormulario.getStatus());
        Assertions.assertEquals("Precisa estar matriculado!", erroCriacaoFormulario.getMessage());
    }
}
