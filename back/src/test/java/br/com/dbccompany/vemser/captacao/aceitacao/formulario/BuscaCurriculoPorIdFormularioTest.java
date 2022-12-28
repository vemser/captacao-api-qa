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

@DisplayName("Formulário")
@Epic("Buscar Currículo por idFormulario")
public class BuscaCurriculoPorIdFormularioTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();

    @Test
    @Tag("all")
    @Description("Deve buscar currículo por idFormulario com sucesso")
    public void deveBuscarCurriculoPorIdFormularioComSucesso() {
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
                ;

        formularioService.buscarCurriculoPorIdFormulario(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                ;

        formularioService.deletar(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve não buscar currículo")
    public void deveNaoBuscarCurriculoInxeistentePorIdFormulario() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(FormularioDTO.class)
                ;

        String message = formularioService.buscarCurriculoPorIdFormulario(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract().path("message")
        ;

        assertEquals("Usuário não possui currículo cadastrado.", message);

        formularioService.deletar(formulario.getIdFormulario())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve não buscar currículo")
    public void deveNaoBuscarCurriculoPorIdFormularioInexistente() {
        String message = formularioService.buscarCurriculoPorIdFormulario(19931019)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract().path("message")
                ;

        assertEquals("Erro ao buscar o formulário.", message);
    }

}
