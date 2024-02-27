package br.com.dbccompany.vemser.tests.entrevista;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.CandidatoClient;
import client.EntrevistaClient;

@DisplayName("Endpoint de remoção de entrevista")
public class DeletarEntrevistaPorIdTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static EntrevistaDataFactory entrevistaDataFactory = new EntrevistaDataFactory();
    private static EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar entrevista por id com sucesso")
    public void testDeletarEntrevistaPorIdComSucesso() {

        CandidatoCriacaoResponseModel candidatoCriado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;
        Integer idTrilha = candidatoCriado.getFormulario().getTrilhas().get(0).getIdTrilha();

        EntrevistaCriacaoModel entrevistaCriada = entrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado, idTrilha);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaClient.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        var response = entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        var buscaEntrevistaDeletada = entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar entrevista por id sem autenticação")
    public void testDeletarEntrevistaPorIdSemAutenticacao() {

        CandidatoCriacaoResponseModel candidatoCriado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;
        Integer idTrilha = candidatoCriado.getFormulario().getTrilhas().get(0).getIdTrilha();

        EntrevistaCriacaoModel entrevistaCriada = entrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado, idTrilha);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaClient.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        var response = entrevistaClient.deletarEntrevistaPorIdSemAutenticacao(entrevistaCadastrada.getIdEntrevista())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        var deletarEntrevista = entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
