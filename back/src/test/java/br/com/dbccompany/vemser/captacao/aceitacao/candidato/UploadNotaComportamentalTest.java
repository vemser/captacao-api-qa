package br.com.dbccompany.vemser.captacao.aceitacao.candidato;

import br.com.dbccompany.vemser.captacao.builder.CandidatoBuilder;
import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoNotaComportamentalDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Candidato")
@Epic("Upload Nota Comportamental")
public class UploadNotaComportamentalTest {
    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();
    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();

    @Test
    @Tag("all")
    @Description("Deve atualizar nota e parecer comportamental de candidato com sucesso")
    public void deveAtualizarNotaComportamentalDeCandidatoComSucesso() {
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
        CandidatoNotaComportamentalDTO notaComportamental = candidatoBuilder.notaComportamental();
        CandidatoDTO candidatoNota = candidatoService.atualizarNotaComportamental(candidato.getIdCandidato(), Utils.convertCandidatoNotaComportamentalToJson(notaComportamental))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CandidatoDTO.class)
                ;
        assertEquals(notaComportamental.getNotaComportamental(), candidatoNota.getNotaEntrevistaComportamental());
        assertEquals(notaComportamental.getParecerComportamental(), candidatoNota.getParecerComportamental());

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("error") //Esperando subir endpoint corrigido para ter novo response
    @Description("Deve tentar atualizar nota comportamental de candidato com nota invalida negativa")
    public void deveAtualizarNotaTecnicaNegativa() {
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

        CandidatoNotaComportamentalDTO notaComportamental = candidatoBuilder.notaComportamentalNegativa();
        CandidatoDTO candidatoNota = candidatoService.atualizarNotaComportamental(candidato.getIdCandidato(), Utils.convertCandidatoNotaComportamentalToJson(notaComportamental))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CandidatoDTO.class)
                ;
        assertEquals(notaComportamental.getNotaComportamental(), candidatoNota.getNotaEntrevistaComportamental());
        assertEquals(notaComportamental.getParecerComportamental(), candidatoNota.getParecerComportamental());

//        candidatoService.atualizarNotaProvaNegativa(candidato.getIdCandidato())
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.SC_BAD_REQUEST)
//                .body(containsString("notaProva: must be greater than or equal to 0"))
//        ;

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @Tag("error")//Esperando subir endpoint corrigido para ter novo response
    @Description("Deve tentar atualizar nota comportamental do candidato com nota maior que 100")
    public void deveAtualizarNotaTecnicaMaior100() {
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

        CandidatoNotaComportamentalDTO notaComportamental = candidatoBuilder.notaComportamentalMaior100();
        CandidatoDTO candidatoNota = candidatoService.atualizarNotaComportamental(candidato.getIdCandidato(), Utils.convertCandidatoNotaComportamentalToJson(notaComportamental))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CandidatoDTO.class)
                ;
        assertEquals(notaComportamental.getNotaComportamental(), candidatoNota.getNotaEntrevistaComportamental());
        assertEquals(notaComportamental.getParecerComportamental(), candidatoNota.getParecerComportamental());

//        candidatoService.atualizarNotaProvaMaior100(candidato.getIdCandidato())
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.SC_BAD_REQUEST)
//                .body(containsString("notaProva: must be less than or equal to 100"))
//        ;

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }
}
