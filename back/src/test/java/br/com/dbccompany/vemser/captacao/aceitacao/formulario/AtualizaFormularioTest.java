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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Formulário")
@Epic("Atualizar Formulário")
public class AtualizaFormularioTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();

    @Test
    @Tag("error")
    @Description("Deve atualizar formulário com sucesso")
    public void deveAtualizarFormularioComSucesso() {
        // NÃO ATUALIZA MESMO RETORNANDO 200
        // ATUALIZA SOMENTE O ATRIBUTO CURSO

        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        FormularioCreateDTO formularioAtualizadoCreate = formularioBuilder.atualizarFormulario();

        FormularioDTO formularioAtualizado = formularioService
                .atualizarFormulario(formulario.getIdFormulario(), Utils.convertFormularioToJson(formularioAtualizadoCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        assertEquals(formularioAtualizadoCreate.getCurso(), formularioAtualizado.getCurso());

        formularioService.deletar(formularioAtualizado.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve não atualizar formulário")
    public void deveNaoAtualizarFormularioSemPreencherCamposObrigatorios() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        FormularioCreateDTO formularioAtualizadoCreate = formularioBuilder
                .atualizarFormularioSemPreencherCamposObrigatorios();

        formularioService
                .atualizarFormulario(formulario.getIdFormulario(), Utils.convertFormularioToJson(formularioAtualizadoCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body(containsString("curso: O campo Curso não deve ser vazio ou nulo."))
                ;

        formularioService.deletar(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve não atualizar formulário")
    public void deveNaoAtualizarFormularioComIdFormularioInexistente() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        FormularioCreateDTO formularioAtualizadoCreate = formularioBuilder.atualizarFormulario();

        String message = formularioService
                .atualizarFormulario(19931019, Utils.convertFormularioToJson(formularioAtualizadoCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract().path("message")
                ;

        assertEquals("Erro ao buscar o formulário.", message);

        formularioService.deletar(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

}
