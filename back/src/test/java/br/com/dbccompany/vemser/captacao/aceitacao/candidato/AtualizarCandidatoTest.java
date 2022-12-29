package br.com.dbccompany.vemser.captacao.aceitacao.candidato;


import br.com.dbccompany.vemser.captacao.builder.CandidatoBuilder;
import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioDTO;
import br.com.dbccompany.vemser.captacao.service.CandidatoService;
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

@DisplayName("Candidato")
@Epic("Atualizar Candidato")
public class AtualizarCandidatoTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();
    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();

    @Test
    @Tag("all")
    @Description("Deve atualizar candidato com sucesso")
    public void deveAtualizarCandidatoComSucesso() {
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

        CandidatoCreateDTO candidatoAtualizadoCreate = candidatoBuilder.atualizarCandidato();
        candidatoAtualizadoCreate.setFormulario(formulario.getIdFormulario());
        CandidatoDTO candidatoAtualizado = candidatoService.atualizarCandidato(candidato.getIdCandidato(), Utils.convertCandidatoToJson(candidatoAtualizadoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CandidatoDTO.class)
                ;
        assertEquals(candidatoAtualizadoCreate.getNome(), candidatoAtualizado.getNome());
        assertEquals(candidatoAtualizadoCreate.getTelefone(), candidatoAtualizado.getTelefone());
        assertEquals(candidatoAtualizadoCreate.getRg(), candidatoAtualizado.getRg());
        assertEquals(candidatoAtualizadoCreate.getEstado(), candidatoAtualizado.getEstado());
        assertEquals(candidatoAtualizadoCreate.getCidade(), candidatoAtualizado.getCidade());

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve tentar atualizar candidato sem preencher campos obrigatorios")
    public void deveAtualizarCandidatoSemPreencherCampos() {
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

        CandidatoCreateDTO candidatoAtualizadoCreate = candidatoBuilder.criarCandidatoSemPreencherCamposObrigatorios();
        candidatoCreate.setFormulario(formulario.getIdFormulario());
        candidatoService.atualizarCandidato(candidato.getIdCandidato(), Utils.convertCandidatoToJson(candidatoAtualizadoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("dataNascimento: must not be null"))
                .body(containsString("nome: O nome deve ter de 3 a 255 caracteres"))
                .body(containsString("telefone: O nome deve ter de 8 a 30 caracteres"))
                .body(containsString("rg: O nome deve ter de 8 a 30 caracteres"))
                .body(containsString("cidade: O nome deve ter de 3 a 30 caracteres"))
                ;

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("all")
    @Description("Deve tentar atualizar candidato com id inexistente")
    public void deveAtualizarCandidatoComIdInexistente() {

        CandidatoCreateDTO candidatoAtualizadoCreate = candidatoBuilder.criarCandidato();

        String message = candidatoService.atualizarCandidato(0, Utils.convertCandidatoToJson(candidatoAtualizadoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("Candidato não encontrado.", message);
    }
}
