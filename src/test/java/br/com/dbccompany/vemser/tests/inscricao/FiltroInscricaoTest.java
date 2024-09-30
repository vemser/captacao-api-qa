package br.com.dbccompany.vemser.tests.inscricao;

import client.CandidatoClient;
import client.EdicaoClient;
import client.FormularioClient;
import client.InscricaoClient;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoListaResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @DisplayName("Cenário 1: Deve filtrar inscrição com sucesso")
    @Tag("Regression")
    public void testDeveFiltrarInscricaoComSucesso(){
        InscricaoListaResponseModel filtroInscricao = inscricaoClient.filtrarInscricao("0","10",
            inscricaoCadastrada.getCandidato().getEmail(),
            candidatoCadastrado.getEdicao().getNome(),
            candidatoCadastrado.getFormulario().getTrilhas().get(0).getNome(),true)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
                .as(InscricaoListaResponseModel.class);
        assertNotNull(filtroInscricao);
        assertEquals(filtroInscricao.getElementos().get(0).getIdInscricao(), inscricaoCadastrada.getIdInscricao());
    }

    @Test
    @DisplayName("Cenário 2: Tentar filtrar inscrição sem token")
    @Tag("Regression")
    public void testTentarFiltrarInscricaoSemToken(){
        inscricaoClient.filtrarInscricao("0","10",
            inscricaoCadastrada.getCandidato().getEmail(),
            candidatoCadastrado.getEdicao().getNome(),
            candidatoCadastrado.getFormulario().getTrilhas().get(0).getNome(),false)
        .then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
