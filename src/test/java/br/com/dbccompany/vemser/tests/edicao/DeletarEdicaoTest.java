package br.com.dbccompany.vemser.tests.edicao;

import client.EdicaoClient;
import factory.EdicaoDataFactory;
import io.restassured.response.Response;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de remoção de edição")
class DeletarEdicaoTest {

    private static final EdicaoClient edicaoClient = new EdicaoClient();
	EdicaoModel edicaoCadastrada;
	EdicaoModel edicaoResponse;

	@BeforeEach
	void setUp() {
		edicaoCadastrada = EdicaoDataFactory.edicaoValida();
		edicaoResponse = edicaoClient.criarEdicao(edicaoCadastrada);
	}

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar edição com sucesso")
    @Tag("Regression")
    void testDeletarEdicaoComSucesso() {

        Integer idEdicao = Integer.parseInt(String.valueOf(edicaoResponse.getIdEdicao()));

        edicaoClient.deletarEdicao(idEdicao)
				.then()
				.statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar edição sem autenticação")
    @Tag("Regression")
    void testDeletarEdicaoSemAutenticacao() {

		edicaoClient.deletarEdicaoSemAutenticacao(edicaoCadastrada.getIdEdicao())
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
