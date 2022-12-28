package br.com.dbccompany.vemser.captacao.aceitacao.candidato;

import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Candidato")
@Epic("Cadastrar Candidato")
public class CadastrarCandidatoTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();
    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();

    @Test
    @Tag("all")
    @Description("Deve cadastrar candidato com sucesso")
    public void deveCadastrarCandidatoComSucesso() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(FormularioDTO.class)
                ;
//        formularioService.atualizarPrintConfigPc(formulario.getIdFormulario())
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.SC_OK)
//                ;
//        formularioService.atualizarCurriculo(formulario.getIdFormulario())
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.SC_OK)
//        ;
        CandidatoCreateDTO candidatoCreate = candidatoBuilder.criarCandidato();
        candidatoCreate.setFormulario(formulario.getIdFormulario());
        CandidatoDTO candidato = candidatoService.cadastroCandidato(Utils.convertCandidatoToJson(candidatoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().as(CandidatoDTO.class)
                ;
        assertEquals(candidatoCreate.getNome(), candidato.getNome());
        assertEquals(candidatoCreate.getCidade(), candidato.getCidade());
        assertEquals(candidatoCreate.getEmail(), candidato.getEmail());
        assertEquals(candidatoCreate.getFormulario(), candidato.getFormulario().getIdFormulario());

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                ;
    }

    @Test
    @Tag("all")
    @Description("Deve não cadastrar candidato")
    public void deveCadastrarCandidatoSemPreencherCamposObrigatorios() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(FormularioDTO.class)
                ;
        CandidatoCreateDTO candidatoCreate = candidatoBuilder.criarCandidatoSemPreencherCamposObrigatorios();
        candidatoService.cadastroCandidato(Utils.convertCandidatoToJson(candidatoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                ;
    }

    @Test
    @Tag("all")
    @Description("Deve não cadastrar candidato")
    public void deveCadastrarCandidatoSemFormularioExistente() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(FormularioDTO.class)
                ;
        CandidatoCreateDTO candidatoCreate = candidatoBuilder.criarCandidato();
        candidatoService.cadastroCandidato(Utils.convertCandidatoToJson(candidatoCreate))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }
}

