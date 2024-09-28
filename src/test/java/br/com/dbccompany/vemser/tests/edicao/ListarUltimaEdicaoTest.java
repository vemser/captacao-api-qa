package br.com.dbccompany.vemser.tests.edicao;

import client.EdicaoClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint que lista a última edição")
class ListarUltimaEdicaoTest {

    private static EdicaoClient edicaoClient = new EdicaoClient();

	@Test
	@DisplayName("Cenário 1: Deve retornar 200 ao listar a última edição com sucesso")
	@Tag("Regression")
	void testListarUltimaEdicaoComSucesso() {

		edicaoClient.obterEdicaoAtual()
				.then()
						.statusCode(HttpStatus.SC_OK);
	}

	@Test
    @DisplayName("Cenário 2: Deve retornar 403 ao listar a última edição sem autenticação")
  	@Tag("Regression")
    void testListarUltimaEdicaoSemAutenticacao() {

        String ultimaEdicao = edicaoClient.listaEdicaoAtualSemAutenticacao();

        Assertions.assertNotNull(ultimaEdicao);
    }
}
