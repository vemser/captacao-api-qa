package br.com.dbccompany.vemser.captacao.aceitacao.formulario;

import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioDTO;
import br.com.dbccompany.vemser.captacao.service.FormularioService;
import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Formulário")
@Epic("Cadastrar Formulário")
public class UploadPrintConfigDeFormularioTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();

    @Test
    @Tag("all")
    @Description("Deve cadastrar formulário com sucesso")
    public void deveCadastrarFormularioComSucesso() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        formularioService.atualizarCurriculo(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        formularioService.atualizarPrintConfigPc(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        assertEquals(formularioCreate.getIngles(), formulario.getIngles());
        assertEquals(formularioCreate.getGenero(), formulario.getGenero());
        assertEquals(formularioCreate.getCurso(), formulario.getCurso());
        assertNotNull(formulario.getCurriculo());
        assertNotNull(formulario.getImagemConfigPc());

        formularioService.deletarTeste(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve não cadastrar formulário")
    public void deveNaoCadastrarFormularioSemPreencherCamposObrigatorios() {
        // DEVE RETORNAR MENSAGEM DE ERRO

        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormularioSemPreencherCamposObrigatorios();

        formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    //.extract().path("message")
                ;

        //assertEquals("", message);
    }

    @Test
    @Tag("all")
    @Description("Deve não cadastrar formulário")
    public void deveNaoCadastrarFormularioSemMatricula() {
        // RETORNA 500, DEVE RETORNAR 400 E MENSAGEM DE ERRO

        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormularioSemMatricula();

        formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    //.extract().as(FormularioDTO.class)
                ;
    }

}
