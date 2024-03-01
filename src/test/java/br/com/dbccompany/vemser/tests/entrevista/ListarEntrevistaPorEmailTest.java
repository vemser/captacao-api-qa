package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.auth.Email;

@DisplayName("Endpoint de listagem de entrevistas por email")
class ListarEntrevistaPorEmailTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao buscar entrevista por email do candidato com sucesso")
    void testListaEntrevistaPorEmailComSucesso() {
        String email = Email.getEmail();

        CandidatoCriacaoResponseModel candidatoCriado = candidatoClient.criarECadastrarCandidatoComCandidatoEntityEEmailEspecifico(email)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;
        Integer idTrilha = candidatoCriado.getFormulario().getTrilhas().get(0).getIdTrilha();

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado, idTrilha);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaClient.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        EntrevistaCriacaoResponseModel entrevista = entrevistaClient.listarTodasAsEntrevistasPorEmail(emailDoCandidato)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        var deletarEntrevista = entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertNotNull(entrevista);
        Assertions.assertEquals(emailDoCandidato, entrevista.getCandidatoEmail());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao buscar entrevista por email do candidato sem autenticação")
    void testListaEntrevistaPorEmailSemAutenticacao() {
        String emailDoCandidato = Email.getEmail();

        var response = entrevistaClient.listarTodasAsEntrevistasPorEmailSemAutenticacao(emailDoCandidato)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
