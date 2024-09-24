package client.entrevista;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.entrevista.EntrevistaCriacaoModel;
import specs.entrevista.EntrevistaSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class EntrevistaClient {

    public static final String ENTREVISTA_MARCAR_ENTREVISTA = "/entrevista/marcar-entrevista";
    public static final String ENTREVISTA = "/entrevista";
    public static final String ENTREVISTA_POR_ID = "/entrevista/atualizar-observacao-entrevista/{idEntrevista}";
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
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                        .body(entrevista)
                .when()
                        .post(ENTREVISTA_MARCAR_ENTREVISTA)
                ;
    }

    public Response listarTodasAsEntrevistas() {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(ENTREVISTA)
                ;
    }

    public Response listarTodasAsEntrevistasPorEmail(String emailDoCandidato) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(ENTREVISTA_BUSCAR_ENTREVISTA_EMAIL_CANDIDATO_EMAIL + emailDoCandidato)
                ;
    }

    public Response listarTodasAsEntrevistasPorEmailSemAutenticacao(String emailDoCandidato) {
        Auth.usuarioAluno();
        return
                given()
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                .when()
                        .get(ENTREVISTA_BUSCAR_ENTREVISTA_EMAIL_CANDIDATO_EMAIL + emailDoCandidato)
                ;
    }

    public Response listarTodasAsEntrevistasPorTrilha(String trilha) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
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
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                        .queryParam(TRILHA, trilha)
                .when()
                        .get(ENTREVISTA_POR_TRILHA)
                ;
    }

    public Response listarTodasAsEntrevistasPorMes(Integer anoEntrevista, Integer mesEntrevista) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
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
						.spec(EntrevistaSpecs.entrevistaReqSpec())
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
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                .when()
                        .get(ENTREVISTA)
                ;
    }

    public Response atualizarEntrevistaSemAutenticacao(Integer idEntrevista, String status, EntrevistaCriacaoModel dadosAtualizados) {
        Auth.usuarioAluno();
        return
                given()
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                        .pathParam(ID_ENTREVISTA1, idEntrevista)
                        .queryParam(LEGENDA, status)
                        .body(dadosAtualizados)
                .when()
                        .put(ENTREVISTA_ATUALIZAR_ENTREVISTA_ID_ENTREVISTA)
                ;
    }

    public Response deletarEntrevistaPorIdSemAutenticacao(Integer idEntrevista) {
        return
                given()
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                        .pathParam(ID_ENTREVISTA1, idEntrevista)
                .when()
                        .delete(ENTREVISTA_ID_ENTREVISTA)
                ;
    }

    public Response deletarEntrevistaPorEmailSemAutenticacao(String email) {
        return
                given()
                        .spec(EntrevistaSpecs.entrevistaReqSpec())
                        .pathParam(EMAIL, email)
                .when()
                        .delete(ENTREVISTA_EMAIL_ENTREVISTA)
                ;
    }

	public Response listarEntrevistasGestor() {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(EntrevistaSpecs.entrevistaReqSpec())
						.header(AUTHORIZATION, AuthClient.getToken())
				.when()
						.get(ENTREVISTAS_GESTOR)
				;
	}

	public Response listarEntrevistasGestorComTokenInvalido() {
		Auth.usuarioInvalido();

		return
				given()
						.spec(EntrevistaSpecs.entrevistaReqSpec())
				.when()
						.get(ENTREVISTAS_GESTOR)
				;
	}

	public Response atualizarEntrevistaPorId(Integer idEntrevista, String observacao) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(EntrevistaSpecs.entrevistaReqSpec())
						.header(AUTHORIZATION, AuthClient.getToken())
						.pathParam(ID_ENTREVISTA, idEntrevista)
						.queryParam(OBSERVACAO, observacao)
				.when()
						.put(ENTREVISTA_POR_ID)
				;
	}

	public Response atualizarEntrevistaPorIdSemAutenticacao(Integer idEntrevista, String observacao) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(EntrevistaSpecs.entrevistaReqSpec())
						.pathParam(ID_ENTREVISTA, idEntrevista)
						.queryParam(OBSERVACAO, observacao)
				.when()
						.put(ENTREVISTA_POR_ID)
				;
	}
}
