package br.com.dbccompany.vemser.tests.formulario;

import client.FormularioClient;
import client.TrilhaClient;
import factory.FormularioDataFactory;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de atualização do formulário")
class AtualizarFormularioTest{

    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final String PATH_SCHEMA_PUT_FORMULARIO = "schemas/formulario/put_formulario.json";
    private static final String TRILHA_VALIDA = "FRONTEND";

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 1: Validação de contrato de atualizar formulario")
    public void testValidarContratoAtualizarFormulario() {
        List<String> listaDeNomeDeTrilhas = new ArrayList<>();

        listaDeNomeDeTrilhas.add(TRILHA_VALIDA);

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        FormularioCriacaoModel formularioInstituicaoAtualizada = FormularioDataFactory.formularioComInstituicaoAtualizada(formulario);

        formularioClient.atualizaFormularioContrato(
                formularioCriado.getIdFormulario(),
                formularioInstituicaoAtualizada
        )
                .then()
                    .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_PUT_FORMULARIO))
                ;

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 2: Deve retornar 200 ao atualizar formulário com sucesso")
    void testAtualizarFormularioComSucesso() {

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

        FormularioCriacaoModel formularioInstituicaoAtualizada = FormularioDataFactory.formularioComInstituicaoAtualizada(formulario);

        FormularioCriacaoResponseModel formularioAtualizado = formularioClient.atualizaFormulario(
                formularioCriado.getIdFormulario(),
                formularioInstituicaoAtualizada
        );

        Assertions.assertEquals(formularioCriado.getIdFormulario(), formularioAtualizado.getIdFormulario());
        Assertions.assertEquals(formularioCriado.getMatriculado(), formularioAtualizado.getMatriculado());
        Assertions.assertEquals(formularioCriado.getCurso(), formularioAtualizado.getCurso());
        Assertions.assertEquals(formularioCriado.getTurno(), formularioAtualizado.getTurno());

        Assertions.assertEquals(formularioInstituicaoAtualizada.getInstituicao(), formularioAtualizado.getInstituicao());

        Assertions.assertEquals(formularioCriado.getGithub(), formularioAtualizado.getGithub());
        Assertions.assertEquals(formularioCriado.getLinkedin(), formularioAtualizado.getLinkedin());
        Assertions.assertEquals(formularioCriado.getDesafios(), formularioAtualizado.getDesafios());
        Assertions.assertEquals(formularioCriado.getProblema(), formularioAtualizado.getProblema());
        Assertions.assertEquals(formularioCriado.getReconhecimento(), formularioAtualizado.getReconhecimento());
        Assertions.assertEquals(formularioCriado.getAltruismo(), formularioAtualizado.getAltruismo());
        Assertions.assertEquals(formularioCriado.getResposta(), formularioAtualizado.getResposta());
        Assertions.assertEquals(formularioCriado.getCurriculo(), formularioAtualizado.getCurriculo());
        Assertions.assertEquals(formularioCriado.getLgpd(), formularioAtualizado.getLgpd());
        Assertions.assertEquals(formularioCriado.getProva(), formularioAtualizado.getProva());
        Assertions.assertEquals(formularioCriado.getIngles(), formularioAtualizado.getIngles());
        Assertions.assertEquals(formularioCriado.getEspanhol(), formularioAtualizado.getEspanhol());
        Assertions.assertEquals(formularioCriado.getNeurodiversidade(), formularioAtualizado.getNeurodiversidade());
        Assertions.assertEquals(formularioCriado.getEfetivacao(), formularioAtualizado.getEfetivacao());
        Assertions.assertEquals(formularioCriado.getGenero(), formularioAtualizado.getGenero());
        Assertions.assertEquals(formularioCriado.getOrientacao(), formularioAtualizado.getOrientacao());
        Assertions.assertEquals(formularioCriado.getDisponibilidade(), formularioAtualizado.getDisponibilidade());

        formularioCriado.getTrilhas().forEach(trilhaOriginal -> formularioAtualizado.getTrilhas().forEach(trilhaAtualizado -> {
            Assertions.assertEquals(trilhaOriginal.getIdTrilha(), trilhaAtualizado.getIdTrilha());
            Assertions.assertEquals(trilhaOriginal.getNome(), trilhaAtualizado.getNome());
        }));

        Assertions.assertEquals(formularioCriado.getImagemConfigPc(), formularioAtualizado.getImagemConfigPc());
        Assertions.assertEquals(formularioCriado.getImportancia(), formularioAtualizado.getImportancia());
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 200 ao realizar upload de comprovante de matricula com sucesso")
    @Tag("Regression")
    void testUploadComprovanteMatriculaComSucesso() {

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

        formularioClient.incluiComprovanteMatriculaComValidacao(formularioCriado.getIdFormulario())
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;

        formularioClient.deletarFormulario(formularioCriado.getIdFormulario());

    }
}
