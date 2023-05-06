package br.com.dbccompany.vemser.tests.formulario;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.FormularioDataFactory;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.FormularioService;
import service.TrilhaService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de atualização do formulário")
public class AtualizarFormularioTest extends BaseTest {

    private static TrilhaService trilhaService = new TrilhaService();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static FormularioService formularioService = new FormularioService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao atualizar atualizar formulário com sucesso")
    public void testAtualizarFormularioComSucesso() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                            .statusCode(HttpStatus.SC_OK)
                            .extract()
                            .as(TrilhaModel[].class))
                            .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormularioComFormularioEntity(formulario);

        FormularioCriacaoModel formularioInstituicaoAtualizada = formularioDataFactory
                .formularioComInstituicaoAtualizada(formulario);

        FormularioCriacaoResponseModel formularioAtualizado = formularioService.atualizaFormulario(
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
        Assertions.assertEquals(formularioCriado.getEtnia(), formularioAtualizado.getEtnia());
        Assertions.assertEquals(formularioCriado.getEfetivacao(), formularioAtualizado.getEfetivacao());
        Assertions.assertEquals(formularioCriado.getGenero(), formularioAtualizado.getGenero());
        Assertions.assertEquals(formularioCriado.getOrientacao(), formularioAtualizado.getOrientacao());
        Assertions.assertEquals(formularioCriado.getDisponibilidade(), formularioAtualizado.getDisponibilidade());

        formularioCriado.getTrilhas().stream().forEach(trilhaOriginal -> {
            formularioAtualizado.getTrilhas().stream().forEach(trilhaAtualizado -> {
                Assertions.assertEquals(trilhaOriginal.getIdTrilha(), trilhaAtualizado.getIdTrilha());
                Assertions.assertEquals(trilhaOriginal.getNome(), trilhaAtualizado.getNome());
            });
        });

        Assertions.assertEquals(formularioCriado.getImagemConfigPc(), formularioAtualizado.getImagemConfigPc());
        Assertions.assertEquals(formularioCriado.getImportancia(), formularioAtualizado.getImportancia());
    }

//    @Test
//    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar formulário sem autenticação")
//    public void testAtualizarFormularioCSemAutenticacao() {
//
//        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
//        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
//                        .then()
//                        .statusCode(HttpStatus.SC_OK)
//                        .extract()
//                        .as(TrilhaModel[].class))
//                .toList();
//
//        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());
//
//        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);
//
//        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormularioComFormularioEntity(formulario);
//
//        FormularioCriacaoModel formularioInstituicaoAtualizada = formularioDataFactory
//                .formularioComInstituicaoAtualizada(formulario);
//
//        var response = formularioService.atualizaFormularioSemAutenticacao(
//                formularioCriado.getIdFormulario(),
//                formularioInstituicaoAtualizada
//        )
//                .then()
//                    .statusCode(HttpStatus.SC_FORBIDDEN);
//    }
}
