package br.com.dbccompany.vemser.captacao.aceitacao.formulario;

import br.com.dbccompany.vemser.captacao.dto.formulario.PageFormularioDTO;
import br.com.dbccompany.vemser.captacao.service.FormularioService;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Formulário")
@Epic("Listar Formulários")
public class ListaFormulariosTest {

    FormularioService formularioService = new FormularioService();

    @Test
    @Tag("all")
    @Description("Deve listar formulários com sucesso")
    public void deveListarFormulariosComSucesso() {
        PageFormularioDTO pageFormulario = formularioService.listar()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PageFormularioDTO.class)
                ;

        assertNotNull(pageFormulario.getElementos());
        assertEquals(0, pageFormulario.getPagina());
        assertEquals(10, pageFormulario.getTamanho());
    }

    @Test
    @Tag("all")
    @Description("Deve listar formulários com ordenação com sucesso")
    public void deveListarFormulariosComOrdenacaoComSucesso() {
        // RETORNA 500, DEVERIA RETORNAR LISTA

        PageFormularioDTO pageFormulario = formularioService.listarComOrdenacao("curriculo")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PageFormularioDTO.class)
                ;

        assertNotNull(pageFormulario.getElementos());
        assertEquals(0, pageFormulario.getPagina());
        assertEquals(10, pageFormulario.getTamanho());
    }

    @Test
    @Tag("wip")
    @Description("Deve retornar lista vazia")
    public void deveRetornarListaVazia() {
        // RETORNA 500, DEVERIA RETORNAR LISTA VAZIA

        PageFormularioDTO pageFormulario = formularioService.listarComPaginacao(0, 0, "idFormulario")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PageFormularioDTO.class)
                ;

        assertTrue(pageFormulario.getElementos().isEmpty());
        assertEquals(0, pageFormulario.getPagina());
        assertEquals(0, pageFormulario.getTamanho());
    }

}
