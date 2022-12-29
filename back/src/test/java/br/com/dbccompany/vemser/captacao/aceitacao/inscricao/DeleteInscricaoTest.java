package br.com.dbccompany.vemser.captacao.aceitacao.inscricao;


import br.com.dbccompany.vemser.captacao.builder.CandidatoBuilder;
import br.com.dbccompany.vemser.captacao.builder.FormularioBuilder;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioDTO;
import br.com.dbccompany.vemser.captacao.dto.inscricao.InscricaoDTO;
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

@DisplayName("Inscrição")
@Epic("Deletar Inscrição")
public class DeleteInscricaoTest {

    FormularioService formularioService = new FormularioService();
    FormularioBuilder formularioBuilder = new FormularioBuilder();
    CandidatoService candidatoService = new CandidatoService();
    CandidatoBuilder candidatoBuilder = new CandidatoBuilder();

    InscricaoService inscricaoService = new InscricaoService();

    @Test
    @Tag("all")
    @Description("Deve cadastrar inscricao candidato com sucesso")
    public void deveCadastrarInscricaoCandidatoComSucesso() {

        String message = inscricaoService.deletarInscricao(0)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract().path("message")
                ;
        assertEquals("ID_Inscrição inválido", message);
    }
}
