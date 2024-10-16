package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.edicao.EdicaoModel;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class EdicaoClient extends BaseClient {

	public static final String AUTHORIZATION = "Authorization";
	public static final String EDICAO_LISTAR_TODAS = "/edicao/listar-todas";
	public static final String EDICAO_EDICAO_ATUAL = "/edicao/edicao-atual";
	public static final String EDICAO_CRIAR_EDICAO = "/edicao/criar-edicao";
	public static final String EDICAO_DELETE_FISICO_ID_EDICAO = "/edicao/delete-fisico/{idEdicao}";
	public static final String ID_EDICAO = "idEdicao";
	public static final String NUMERO_EDICAO = "numeroEdicao";
	public static final String NOTA_DE_CORTE = "/edicao/update-nota-corte";

	public Response listarTodasAsEdicoes() {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
				.when()
						.get(EDICAO_LISTAR_TODAS);
	}

	public Response listarTodasAsEdicoesSemAutenticacao() {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
				.when()
						.get(EDICAO_LISTAR_TODAS);
	}

	public String listaEdicaoAtualSemAutenticacao() {
		Auth.usuarioAluno();

		return
				given()
						.spec(setUp())
				.when()
						.get(EDICAO_EDICAO_ATUAL)
						.thenReturn()
						.asString();
	}

	public Response obterEdicaoAtual() {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
				.when()
						.get(EDICAO_EDICAO_ATUAL);
	}

	public String getEdicaoAtualComoString() {
		return obterEdicaoAtual()
				.then()
					.extract()
					.asString();
	}

	public EdicaoModel criarEdicao(EdicaoModel edicao) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.contentType(ContentType.JSON)
						.body(edicao)
				.when()
						.post(EDICAO_CRIAR_EDICAO)
				.then()
						.extract()
						.as(EdicaoModel.class);
	}

	public Response cadastrarEdicao(EdicaoModel model) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.header(AUTHORIZATION, AuthClient.getToken())
						.spec(setUp())
						.contentType(ContentType.JSON)
						.body(model)
				.when()
						.post(EDICAO_CRIAR_EDICAO);
	}

	public Response criarEdicaoComNumEdicaoSemAutenticacao(Integer numeroEdicao) {
		Auth.usuarioAluno();
		return
				given()
						.spec(setUp())
						.queryParam(NUMERO_EDICAO, numeroEdicao)
				.when()
						.post(EDICAO_CRIAR_EDICAO);
	}

	public Response deletarEdicao(Integer idEdicao) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.pathParam(ID_EDICAO, idEdicao)
				.when()
						.delete(EDICAO_DELETE_FISICO_ID_EDICAO);
	}

	public Response deletarEdicaoSemAutenticacao(Integer idEdicao) {
		Auth.usuarioAluno();

		return
				given()
						.spec(setUp())
						.pathParam(ID_EDICAO, idEdicao)
				.when()
						.delete(EDICAO_DELETE_FISICO_ID_EDICAO);
	}

	public Response atualizarNotaDeCorte(EdicaoModel edicao) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.contentType(ContentType.JSON)
						.body(edicao)
						.header(AUTHORIZATION, AuthClient.getToken())
				.when()
						.put(NOTA_DE_CORTE);
	}

	public Response atualizarNotaDeCorteSemAutenticacao(EdicaoModel edicao) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.contentType(ContentType.JSON)
						.body(edicao)
				.when()
						.put(NOTA_DE_CORTE);
	}

}
