package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de entrevista")
class DeletarEntrevistaPorIdTest{

    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar entrevista por id com sucesso")
    void testDeletarEntrevistaPorIdComSucesso() {

            CandidatoCriacaoResponseModel candidatoCriado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                    .then()
                  	    .statusCode(HttpStatus.SC_CREATED)
					    .extract()
                    	.as(CandidatoCriacaoResponseModel.class);

            String emailDoCandidato = candidatoCriado.getEmail();
            Boolean candidatoAvaliado = true;

            EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado);

            EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaClient.cadastrarEntrevista(entrevistaCriada)
                    .then()
                    	.statusCode(HttpStatus.SC_CREATED)
					    .extract()
                    	.as(EntrevistaCriacaoResponseModel.class);

            entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                    .then()
						.statusCode(HttpStatus.SC_NO_CONTENT);

            entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                    .then()
                   	 .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao tentar deletar entrevista por id sem autenticação")
    void testDeletarEntrevistaPorIdSemAutenticacao() {

        int idEntrevista = entrevistaClient.listarTodasAsEntrevistas()
                .then()
                	.statusCode(HttpStatus.SC_OK)
				    .extract()
				    .path("[0].idEntrevista");


        entrevistaClient.deletarEntrevistaPorIdSemAutenticacao(idEntrevista)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
   }
}
