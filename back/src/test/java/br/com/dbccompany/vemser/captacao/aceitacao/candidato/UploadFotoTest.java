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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Candidato")
@Epic("Upload Foto")
public class UploadFotoTest {

    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();
    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();

    @Test
    @Tag("all")
    @Description("Deve atualizar foto de candidato com sucesso")
    public void deveAtualizarFotoDeCandidatoComSucesso() {
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

        candidatoService.atualizarFoto(candidato.getEmail())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                ;

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
                ;
    }

    @Test
    @Tag("all")
    @Description("Deve não atualizar foto de candidato")
    public void deveNaoAtualizarFotoDeCandidatoComEmailInvalido() {
        String message = candidatoService.atualizarFoto("email.invalido")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract().path("message")
                    ;

        assertEquals("Candidato com o e-mail especificado não existe", message);
    }

    @Test
    @Tag("all")
    @Description("Deve não atualizar foto de candidato")
    public void deveNaoAtualizarFotoDeCandidatoComFormatoInvalido() {
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

        String message = candidatoService.atualizarFotoInvalido(candidato.getEmail())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract().path("message")
        ;

        assertEquals("Formato de arquivo inválido! Inserir .png, .jpg ou .jpeg", message);

        candidatoService.deletarTesteFisico(candidato.getIdCandidato())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

}
