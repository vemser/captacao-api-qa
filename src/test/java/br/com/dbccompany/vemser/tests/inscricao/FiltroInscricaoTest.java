package br.com.dbccompany.vemser.tests.inscricao;

import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.inscricao.InscricaoClient;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoListaResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Endpoint de filtro de inscrição")
public class FiltroInscricaoTest {
    private static final InscricaoClient inscricaoClient = new InscricaoClient();
    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static CandidatoCriacaoResponseModel candidatoCadastrado;
    private static InscricaoModel inscricaoCadastrada;

    @BeforeAll
    public static void criarMassaTeste(){
        candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);
        inscricaoCadastrada = inscricaoClient.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(InscricaoModel.class);
    }
    @AfterAll
    public static void deletarMassaTeste(){
        inscricaoClient.deletarInscricao(inscricaoCadastrada.getIdInscricao());
        formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
        candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
        edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
    }

    @Test
    @DisplayName("Cenário 1: deve filtrar inscrição com sucesso")
    public void testDeveFiltrarInscricaoComSucesso(){
        InscricaoListaResponseModel filtroInscricao = inscricaoClient.filtrarInscricao("0",
                "10",
                inscricaoCadastrada.getCandidato().getEmail(),
                candidatoCadastrado.getEdicao().getNome(),
                candidatoCadastrado.getFormulario().getTrilhas().get(0).getNome(),
                true)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(InscricaoListaResponseModel.class);
        assertNotNull(filtroInscricao);
        assertEquals(filtroInscricao.getElementos().get(0).getIdInscricao(), inscricaoCadastrada.getIdInscricao());
    }

}
