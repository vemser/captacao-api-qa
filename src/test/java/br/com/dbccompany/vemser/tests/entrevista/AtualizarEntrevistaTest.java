package br.com.dbccompany.vemser.tests.entrevista;

import client.EdicaoClient;
import client.EntrevistaClient;
import factory.EntrevistaDataFactory;
import io.restassured.response.Response;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de entrevista")
class AtualizarEntrevistaTest  {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
	private static final EdicaoClient edicaoClient = new EdicaoClient();
	private static final String STATUS_ENTREVISTA = "CONFIRMADA";
	private static final Boolean CANDIDATO_AVALIADO = true;

	@Test
	@DisplayName("Cenário 1: Deve retornar 204 ao atualizar entrevista com sucesso")
	@Tag("Functional")
	void testAtualizarEntrevistaComSucesso() {

		String edicao = edicaoClient.obterEdicaoAtual()
				.then()
				.extract().asString();

		EntrevistaCriacaoResponseModel[] listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistas(edicao)
				.then()
					.statusCode(HttpStatus.SC_OK)
					.extract()
					.as(EntrevistaCriacaoResponseModel[].class);

		int primeiraEntrevistaId = listaDeEntrevistas[0].getIdEntrevista();

		Response response = EntrevistaDataFactory.buscarTodasEntrevistas();
		String emailEntrevista = response.path("[0].candidatoDTO.email");

		EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailEntrevista, CANDIDATO_AVALIADO);

		entrevistaClient.atualizarEntrevista(primeiraEntrevistaId, STATUS_ENTREVISTA, entrevistaCriada)
				.then()
					.statusCode(HttpStatus.SC_NO_CONTENT);

	}

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao atualizar entrevista sem estar autenticado")
    @Tag("Regression")
    void testAtualizarEntrevistaSemAutenticacao() {

		String edicao = edicaoClient.obterEdicaoAtual()
				.then()
				.extract().asString();

		EntrevistaCriacaoResponseModel[] listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistas(edicao)
                .then()
                	.statusCode(HttpStatus.SC_OK)
                	.extract()
                	.as(EntrevistaCriacaoResponseModel[].class);

        int primeiraEntrevistaId = listaDeEntrevistas[0].getIdEntrevista();

		Response response = EntrevistaDataFactory.buscarTodasEntrevistas();
		String emailEntrevista = response.path("[0].candidatoDTO.email");

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaCriacaoValida(emailEntrevista, CANDIDATO_AVALIADO);

        entrevistaClient.atualizarEntrevistaSemAutenticacao(primeiraEntrevistaId, STATUS_ENTREVISTA, entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }
}
