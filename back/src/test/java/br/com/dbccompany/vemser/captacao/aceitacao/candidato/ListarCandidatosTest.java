package br.com.dbccompany.vemser.captacao.aceitacao.candidato;

import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.PageCandidatoDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioDTO;
import br.com.dbccompany.vemser.captacao.builder.CandidatoBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.service.CandidatoService;
import br.com.dbccompany.vemser.captacao.service.FormularioService;
import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Candidato")
@Epic("Listar Candidato")
public class ListarCandidatosTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();
    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();

    @Test
    @Tag("all")
    @Description("Deve listar candidatos com sucesso")
    public void deveListarCandidatosComSucesso() {
        PageCandidatoDTO pageCandidatos = candidatoService.buscarListaPorId()
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(PageCandidatoDTO.class)
                ;

        assertNotNull(pageCandidatos.getElementos());
        assertEquals(0, pageCandidatos.getPagina());
        assertEquals(20, pageCandidatos.getTamanho());
    }

    @Test
    @Tag("all")
    @Description("Deve listar candidato pelo email")
    public void deveListarCandidatoPeloEmail() {
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

        CandidatoDTO candidatoBuscadoEmail =candidatoService.buscarCandidatoPorEmail(candidato.getEmail())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CandidatoDTO.class)
        ;
        assertEquals(candidatoCreate.getNome(), candidatoBuscadoEmail.getNome());
        assertEquals(candidatoCreate.getCidade(), candidatoBuscadoEmail.getCidade());
        assertEquals(candidatoCreate.getEmail(), candidatoBuscadoEmail.getEmail());


        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve tentar listar candidato pelo email inexistente")
    public void deveListarCandidatoPeloEmailInexistente() {

        String message =candidatoService.buscarCandidatoPorEmail("0")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("Candidato com o e-mail especificado não existe", message);
    }

    @Test
    @Tag("all")
    @Description("Deve listar candidatos pela trilha")
    public void deveListarCandidatosPorTrilha() {

        candidatoService.buscarCandidatosPorTrilha("qa")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                ;
    }

    @Test
    @Tag("all")
    @Description("Deve tentar listar candidatos pela trilha inexistente")
    public void deveListarCandidatosPorTrilhaInexistente() {

        String message = candidatoService.buscarCandidatosPorTrilha("hahahaha")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("Trilha não encontrada!", message);
    }

    @Test
    @Tag("all")
    @Description("Deve listar candidatos pela edicao")
    public void deveListarCandidatosPorEdicao() {

        candidatoService.buscarCandidatosPorEdicao("1ª Edição")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                ;
    }

    @Test
    @Tag("all")
    @Description("Deve tentar listar candidatos pela edicao inexistente")
    public void deveListarCandidatosPorEdicaoInexistente() {

        String message = candidatoService.buscarCandidatosPorEdicao("0ª Edição")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("Edição não encontrada!", message);
    }
}
