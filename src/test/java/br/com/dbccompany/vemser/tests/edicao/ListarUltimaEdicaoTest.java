package br.com.dbccompany.vemser.tests.edicao;

import client.edicao.EdicaoClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import factory.edicao.EdicaoDataFactory;
import models.edicao.EdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint que lista a última edição")
class ListarUltimaEdicaoTest {

    private static EdicaoClient edicaoClient = new EdicaoClient();

	@Test
	@DisplayName("Cenário 1: Deve retornar 200 ao listar a última edição com sucesso")
	void testListarUltimaEdicaoComSucesso() throws JsonProcessingException {

		var response = edicaoClient.listarTodasAsEdicoes()
				.then()
				.statusCode(HttpStatus.SC_OK)
				.extract()
				.response();

		String jsonResponse = response.asString().replace("\"notaCorte\":\"NaN\"", "\"notaCorte\":null");

		List<EdicaoModel> listaDeEdicoes = Arrays.asList(new ObjectMapper().readValue(jsonResponse, EdicaoModel[].class));

		listaDeEdicoes.sort((edicao1, edicao2) -> edicao2.getIdEdicao().compareTo(edicao1.getIdEdicao()));


		EdicaoModel novaEdicao = EdicaoDataFactory.edicaoValida();

		EdicaoModel edicaoCadastrada = edicaoClient.cadastrarEdicao(novaEdicao)
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.extract()
				.as(EdicaoModel.class);

		response = edicaoClient.listarTodasAsEdicoes()
				.then()
				.statusCode(HttpStatus.SC_OK)
				.extract()
				.response();

		jsonResponse = response.asString().replace("\"notaCorte\":\"NaN\"", "\"notaCorte\":null");

		listaDeEdicoes = Arrays.asList(new ObjectMapper().readValue(jsonResponse, EdicaoModel[].class));

		listaDeEdicoes.sort((edicao1, edicao2) -> edicao2.getIdEdicao().compareTo(edicao1.getIdEdicao()));

		EdicaoModel ultimaEdicao = listaDeEdicoes.get(0);

		edicaoClient.deletarEdicao(edicaoCadastrada.getIdEdicao());

		Assertions.assertNotNull(ultimaEdicao, "A última edição não foi encontrada.");
		Assertions.assertEquals(edicaoCadastrada.getNome().toLowerCase(), ultimaEdicao.getNome().toLowerCase(), "O nome da última edição não corresponde ao nome da edição cadastrada.");
	}

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao listar a última edição sem autenticação")
	@Tag("Regression")
    void testListarUltimaEdicaoSemAutenticacao() {

        String ultimaEdicao = edicaoClient.listaEdicaoAtualSemAutenticacao();

        Assertions.assertNotNull(ultimaEdicao);
    }
}
