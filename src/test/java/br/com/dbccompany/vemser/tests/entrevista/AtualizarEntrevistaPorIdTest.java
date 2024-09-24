package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualização de entrevista por ID")
public class AtualizarEntrevistaPorIdTest {

	public static final EntrevistaClient entrevistaClient = new EntrevistaClient();

	@Test
	@DisplayName("Cenário 2: Deve retornar 204 ao atualizar entrevista por id")
	public void testAtualizarEntrevistaPorID() {

		entrevistaClient.atualizarEntrevistaPorId(26, "AAA")
				.then()
					.statusCode(HttpStatus.SC_NO_CONTENT);
	}

	@Test
	@DisplayName("Cenário 3: Deve retornar 403 ao tentar atualizar entrevista por id sem estar autenticado")
	public void testAtualizarEntrevistaPorIDSemAutenticacao() {

		Integer idEntrevista = EntrevistaDataFactory.buscarTodasEntrevistas().jsonPath().getInt("[0].idEntrevista");
		String observacao = EntrevistaDataFactory.gerarObservacao();

		entrevistaClient.atualizarEntrevistaPorIdSemAutenticacao(idEntrevista, observacao)
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}

	@Test
	@DisplayName("Cenário 4: Deve retornar 400 ao tentar atualizar entrevista com id inexistente")
	public void testAtualizarEntrevistaComIdEntrevistaInexistente() {

		Integer idEntrevistaInexistente = EntrevistaDataFactory.idEntrevistaEnexistente();
		String observacao = EntrevistaDataFactory.gerarObservacao();

		entrevistaClient.atualizarEntrevistaPorId(idEntrevistaInexistente, observacao)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
}
