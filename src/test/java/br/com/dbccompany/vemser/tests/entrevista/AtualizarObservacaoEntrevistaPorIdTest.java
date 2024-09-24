package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de atualizar observação de entrevista por ID")
public class AtualizarObservacaoEntrevistaPorIdTest {

	public static final EntrevistaClient entrevistaClient = new EntrevistaClient();

	@Test
	@DisplayName("Cenário 2: Deve retornar 204 ao atualizar entrevista por id")
	public void testAtualizarObservacaoEntrevistaPorID() {

		entrevistaClient.atualizarObservacaoEntrevistaPorId(26, "AAA")
				.then()
					.statusCode(HttpStatus.SC_NO_CONTENT);
	}

	@Test
	@DisplayName("Cenário 3: Deve retornar 403 ao tentar atualizar entrevista por id sem estar autenticado")
	public void testAtualizarObservacaoEntrevistaPorIDSemAutenticacao() {

		Integer idEntrevista = EntrevistaDataFactory.buscarTodasEntrevistas().jsonPath().getInt("[0].idEntrevista");
		String observacao = EntrevistaDataFactory.gerarObservacao();

		entrevistaClient.atualizarObservacaoEntrevistaPorIdSemAutenticacao(idEntrevista, observacao)
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}

	@Test
	@DisplayName("Cenário 4: Deve retornar 400 ao tentar atualizar entrevista com id inexistente")
	public void testAtualizarObservacaoEntrevistaComIdEntrevistaInexistente() {

		Integer idEntrevistaInexistente = EntrevistaDataFactory.idEntrevistaEnexistente();
		String observacao = EntrevistaDataFactory.gerarObservacao();

		entrevistaClient.atualizarObservacaoEntrevistaPorId(idEntrevistaInexistente, observacao)
				.then()
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
}
