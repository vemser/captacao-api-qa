package br.com.dbccompany.vemser.captacao.aceitacao.candidato;

import br.com.dbccompany.vemser.captacao.builder.CandidatoBuilder;
import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoNotaDTO;
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
@Epic("Upload Nota prova")
public class UploadNotaProvaTest {
    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();
    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();

    @Test
    @Tag("all")
    @Description("Deve atualizar nota da prova de candidato com sucesso")
    public void deveAtualizarNotaDeCandidatoComSucesso() {
        FormularioCreateDTO formularioCreate = formularioBuilder.criarFormulario();

        FormularioDTO formulario = formularioService.cadastrar(Utils.convertFormularioToJson(formularioCreate))
                .then()
//                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(FormularioDTO.class)
                ;

        CandidatoCreateDTO candidatoCreate = candidatoBuilder.criarCandidato();
        candidatoCreate.setFormulario(formulario.getIdFormulario());

        CandidatoDTO candidato = candidatoService.cadastroCandidato(Utils.convertCandidatoToJson(candidatoCreate))
                .then()
//                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().as(CandidatoDTO.class)
                ;
//        System.out.println(nota.getNotaProva());
        CandidatoDTO candidatoNota = candidatoService.atualizarNotaProva(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CandidatoDTO.class)
                ;
        assertEquals(60.0, candidatoNota.getNotaProva());

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }
}
