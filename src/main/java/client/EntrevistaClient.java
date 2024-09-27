package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.entrevista.EntrevistaCriacaoModel;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class EntrevistaClient extends BaseClient {

	public static final String ENTREVISTA_MARCAR_ENTREVISTA = "/entrevista/marcar-entrevista";
	public static final String ENTREVISTA = "/entrevista";
	public static final String OBSERVACAO_ENTREVISTA_POR_ID = "/entrevista/atualizar-observacao-entrevista/{idEntrevista}";
	public static final String ENTREVISTAS_GESTOR = "/entrevista/entrevistas/gestor";
	public static final String ENTREVISTA_BUSCAR_ENTREVISTA_EMAIL_CANDIDATO_EMAIL = "/entrevista/buscar-entrevista-email-candidato/";
	public static final String ENTREVISTA_POR_TRILHA = "/entrevista/por-trilha";
	public static final String ENTREVISTA_LISTAR_POR_MES = "/entrevista/listar-por-mes";
	public static final String ENTREVISTA_ATUALIZAR_ENTREVISTA_ID_ENTREVISTA = "/entrevista/atualizar-entrevista/{idEntrevista}";
	public static final String ENTREVISTA_ID_ENTREVISTA = "/entrevista/{idEntrevista}";
	public static final String ENTREVISTA_EMAIL_ENTREVISTA = "/entrevista/deletar-entrevista-email-candidato/{email}";
	private static final String AUTHORIZATION = "Authorization";
	public static final String ID_ENTREVISTA1 = "idEntrevista";
	public static final String EMAIL = "email";
	public static final String TRILHA = "trilha";
	public static final String MES = "mes";
	public static final String ANO = "ano";
	public static final String ID_ENTREVISTA = "idEntrevista";
	public static final String OBSERVACAO = "observacao";
	public static final String LEGENDA = "legenda";

	public Response cadastrarEntrevistaSemAutenticacao(EntrevistaCriacaoModel entrevista) {
		Auth.usuarioAluno();
		return
				given()
						.spec(super.setUp())
						.contentType(ContentType.JSON)
						.body(entrevista)
				.when()
						.post(ENTREVISTA_MARCAR_ENTREVISTA)
				;
	}

	public Response listarTodasAsEntrevistas(String nomeEntrevista) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(super.setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.queryParam("nomeEntrevista", nomeEntrevista)
				.when()
						.get(ENTREVISTA)
				;
	}

	public Response listarTodasAsEntrevistasPorEmail(String emailDoCandidato) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(super.setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
				.when()
						.get(ENTREVISTA_BUSCAR_ENTREVISTA_EMAIL_CANDIDATO_EMAIL + emailDoCandidato)
				;
	}

	public Response listarTodasAsEntrevistasPorEmailSemAutenticacao(String emailDoCandidato) {
		Auth.usuarioAluno();
		return
				given()
						.spec(super.setUp())
				.when()
						.get(ENTREVISTA_BUSCAR_ENTREVISTA_EMAIL_CANDIDATO_EMAIL + emailDoCandidato)
				;
	}

	public Response listarTodasAsEntrevistasPorTrilha(String trilha) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(super.setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.queryParam(TRILHA, trilha)
				.when()
						.get(ENTREVISTA_POR_TRILHA)
				;
	}

	public Response listarTodasAsEntrevistasPorTrilhaSemAutenticacao(String trilha) {
		Auth.usuarioAluno();

		return
				given()
						.spec(super.setUp())
						.queryParam(TRILHA, trilha)
				.when()
						.get(ENTREVISTA_POR_TRILHA)
				;
	}

	public Response listarTodasAsEntrevistasPorMes(Integer anoEntrevista, Integer mesEntrevista) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(super.setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.queryParam(MES, mesEntrevista)
						.queryParam(ANO, anoEntrevista)
				.when()
						.get(ENTREVISTA_LISTAR_POR_MES)
				;
	}

	public Response listarTodasAsEntrevistasPorMesSemAutenticacao(Integer anoEntrevista, Integer mesEntrevista) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(super.setUp())
						.queryParam(MES, mesEntrevista)
						.queryParam(ANO, anoEntrevista)
				.when()
						.get(ENTREVISTA_LISTAR_POR_MES)
				;
	}

	public Response listarTodasAsEntrevistasSemAutenticacao() {
		Auth.usuarioAluno();

		return
				given()
						.spec(super.setUp())
				.when()
						.get(ENTREVISTA)
				;
	}

	public Response atualizarEntrevista(Integer idEntrevista, String status, EntrevistaCriacaoModel dadosAtualizados) {
		Auth.usuarioAluno();

		return
				given()
						.spec(super.setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.pathParam(ID_ENTREVISTA1, idEntrevista)
						.queryParam(LEGENDA, status)
						.contentType(ContentType.JSON)
						.body(dadosAtualizados)
				.when()
						.put(ENTREVISTA_ATUALIZAR_ENTREVISTA_ID_ENTREVISTA)
				;
	}

	public Response atualizarEntrevistaSemAutenticacao(Integer idEntrevista, String status, EntrevistaCriacaoModel dadosAtualizados) {
		Auth.usuarioAluno();

		return
				given()
						.spec(super.setUp())
						.pathParam(ID_ENTREVISTA1, idEntrevista)
						.queryParam(LEGENDA, status)
						.contentType(ContentType.JSON)
						.body(dadosAtualizados)
				.when()
						.put(ENTREVISTA_ATUALIZAR_ENTREVISTA_ID_ENTREVISTA)
				;
	}

	public Response deletarEntrevistaPorIdSemAutenticacao(Integer idEntrevista) {
		return
				given()
						.spec(super.setUp())
						.pathParam(ID_ENTREVISTA1, idEntrevista)
				.when()
						.delete(ENTREVISTA_ID_ENTREVISTA)
				;
	}

	public Response deletarEntrevistaPorEmailSemAutenticacao(String email) {
		return
				given()
						.spec(super.setUp())
						.pathParam(EMAIL, email)
				.when()
						.delete(ENTREVISTA_EMAIL_ENTREVISTA)
				;
	}

	public Response listarEntrevistasGestor() {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(super.setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
				.when()
						.get(ENTREVISTAS_GESTOR)
				;
	}

	public Response listarEntrevistasGestorComTokenInvalido() {
		Auth.usuarioInvalido();

		return
				given()
						.spec(super.setUp())
				.when()
						.get(ENTREVISTAS_GESTOR)
				;
	}

	public Response atualizarObservacaoEntrevistaPorId(Integer idEntrevista, String observacao) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(super.setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.pathParam(ID_ENTREVISTA, idEntrevista)
						.queryParam(OBSERVACAO, observacao)
				.when()
						.put(OBSERVACAO_ENTREVISTA_POR_ID)
				;
	}

	public Response atualizarObservacaoEntrevistaPorIdSemAutenticacao(Integer idEntrevista, String observacao) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(super.setUp())
						.pathParam(ID_ENTREVISTA, idEntrevista)
						.queryParam(OBSERVACAO, observacao)
				.when()
						.put(OBSERVACAO_ENTREVISTA_POR_ID)
				;
	}
}
