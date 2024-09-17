package br.com.dbccompany.vemser.tests.formulario;

import client.formulario.FormularioClient;
import client.trilha.TrilhaClient;
import factory.formulario.FormularioDataFactory;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de atualização do formulário")
class AtualizarFormularioTest{

    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar formulário com sucesso")
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
}
