package br.com.dbccompany.vemser.captacao.aceitacao.inscricao;


import br.com.dbccompany.vemser.captacao.builder.CandidatoBuilder;
import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioDTO;
import br.com.dbccompany.vemser.captacao.dto.inscricao.InscricaoDTO;
import br.com.dbccompany.vemser.captacao.dto.inscricao.PageDTOInscricaoDTO;
import br.com.dbccompany.vemser.captacao.service.CandidatoService;
import br.com.dbccompany.vemser.captacao.service.FormularioService;
import br.com.dbccompany.vemser.captacao.service.InscricaoService;
import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Inscrição")
@Epic("Listar Inscrição")
public class ListarInscricaoTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();
    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();

    InscricaoService inscricaoService = new InscricaoService();

    @Test
    @Tag("all")
    @Description("Deve listar incricao dos candidatos com sucesso")
    public void deveListarIncricaoCandidatosComSucesso() {
        PageDTOInscricaoDTO pageInscricao = inscricaoService.buscarInscricao()
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(PageDTOInscricaoDTO.class)
                ;

        assertNotNull(pageInscricao.getElementos());
        assertEquals(0, pageInscricao.getPagina());
        assertEquals(10, pageInscricao.getTamanho());
    }

    @Test
    @Tag("error")
    @Description("Deve listar inscricao candidato por email")
    public void deveListarInscricaoCandidatoEmail() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(FormularioDTO.class)
                ;

        CandidatoCreateDTO candidatoCreate = candidatoBuilder.criarCandidato();
        candidatoCreate.setFormulario(formulario.getIdFormulario());
        CandidatoDTO candidato = candidatoService.cadastroCandidato(Utils.convertCandidatoToJson(candidatoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().as(CandidatoDTO.class)
                ;
        InscricaoDTO inscricao = inscricaoService.cadastroInscricao(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(InscricaoDTO.class)
                ;
        InscricaoDTO buscaInscricaoPorEmail =  inscricaoService.buscarInscricaoPorEmail(candidato.getEmail())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(InscricaoDTO.class)
                ;
        assertEquals(candidatoCreate.getNome(), buscaInscricaoPorEmail.getCandidato().getNome());
        assertEquals(candidatoCreate.getCidade(), buscaInscricaoPorEmail.getCandidato().getCidade());
        assertEquals(candidatoCreate.getEmail(), buscaInscricaoPorEmail.getCandidato().getEmail());

        inscricaoService.deletarInscricao(inscricao.getIdInscricao())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("error")
    @Description("Deve tentar listar inscricao candidato pelo email inexistente")
    public void deveListarInscricaoCandidatoPeloEmailInexistente() {

        String message = inscricaoService.buscarInscricaoPorEmail("0")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("Candidato com o e-mail especificado não existe", message);
    }

    @Test
    @Tag("all")
    @Description("Deve listar inscricao candidatos pela trilha")
    public void deveListarInscricaoCandidatosPorTrilha() {

        inscricaoService.buscarInscricaoPorTrilha("qa")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve tentar listar incricao candidatos pela trilha inexistente")
    public void deveListarInscricaoCandidatosPorTrilhaInexistente() {

        String message = inscricaoService.buscarInscricaoPorTrilha("hahahaha")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("Trilha não encontrada!", message);
    }

    @Test
    @Tag("all")
    @Description("Deve listar inscricao candidatos pela edicao")
    public void deveListarInscricaoCandidatosPorEdicao() {

        inscricaoService.buscarInscricaoPorEdicao("1ª Edição")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve tentar listar inscricao candidatos pela edicao inexistente")
    public void deveListarInscricaoCandidatosPorEdicaoInexistente() {

        String message = candidatoService.buscarCandidatosPorEdicao("0ª Edição")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("Edição não encontrada!", message);
    }

    @Test
    @Tag("all")
    @Description("Deve listar inscricao candidato por idInscricao")
    public void deveListarInscricaoCandidatoIdInscricao() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(FormularioDTO.class)
                ;

        CandidatoCreateDTO candidatoCreate = candidatoBuilder.criarCandidato();
        candidatoCreate.setFormulario(formulario.getIdFormulario());
        CandidatoDTO candidato = candidatoService.cadastroCandidato(Utils.convertCandidatoToJson(candidatoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().as(CandidatoDTO.class)
                ;
        InscricaoDTO inscricao = inscricaoService.cadastroInscricao(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(InscricaoDTO.class)
                ;
        InscricaoDTO buscaInscricaoPorIdInscricao =  inscricaoService.buscarInscricaoPorId(inscricao.getIdInscricao())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(InscricaoDTO.class)
                ;
        assertEquals(candidatoCreate.getNome(), buscaInscricaoPorIdInscricao.getCandidato().getNome());
        assertEquals(candidatoCreate.getCidade(), buscaInscricaoPorIdInscricao.getCandidato().getCidade());
        assertEquals(candidatoCreate.getEmail(), buscaInscricaoPorIdInscricao.getCandidato().getEmail());

        inscricaoService.deletarInscricao(inscricao.getIdInscricao())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve listar inscricao candidato por idInscricao Inexistente")
    public void deveListarInscricaoCandidatoIdInscricaoInexistente() {

        String message = inscricaoService.buscarInscricaoPorId(0)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("ID_Inscrição inválido", message);
    }
}
