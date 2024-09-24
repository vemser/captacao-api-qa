package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import io.restassured.response.Response;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de atualização de entrevista")
class AtualizarEntrevistaTest  {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();

	@Test
	@DisplayName("Cenário 1: Deve retornar 204 ao atualizar entrevista com sucesso")
	void testAtualizarEntrevistaComSucesso() {

		String statusEntrevista = "CONFIRMADA";

		EntrevistaCriacaoResponseModel[] listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistas()
				.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.as(EntrevistaCriacaoResponseModel[].class);

		int primeiraEntrevistaId = listaDeEntrevistas[0].getIdEntrevista();

		Response response = EntrevistaDataFactory.buscarTodasEntrevistas();
		String emailEntrevista = response.path("[0].candidatoDTO.email");
		Boolean candidatoAvaliado = true;

		EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailEntrevista, candidatoAvaliado);

		entrevistaClient.atualizarEntrevista(primeiraEntrevistaId, statusEntrevista, entrevistaCriada)
				.then()
					.statusCode(HttpStatus.SC_NO_CONTENT);

	}

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar entrevista sem estar autenticado")
    void testAtualizarEntrevistaSemAutenticacao() {

        String statusEntrevista = "CONFIRMADA";

        EntrevistaCriacaoResponseModel[] listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistas()
                .then()
                	.statusCode(HttpStatus.SC_OK)
                	.extract()
                	.as(EntrevistaCriacaoResponseModel[].class);

        int primeiraEntrevistaId = listaDeEntrevistas[0].getIdEntrevista();

		Response response = EntrevistaDataFactory.buscarTodasEntrevistas();
		String emailEntrevista = response.path("[0].candidatoDTO.email");
		Boolean candidatoAvaliado = true;

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailEntrevista, candidatoAvaliado);

        entrevistaClient.atualizarEntrevistaSemAutenticacao(primeiraEntrevistaId, statusEntrevista, entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
