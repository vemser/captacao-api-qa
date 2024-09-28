package client;

import io.restassured.response.Response;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class StatusClient extends BaseClient {

	public static final String AUTHORIZATION = "Authorization";

	private static final String STATUS_PREVIUS = "/status/previus/{idCandidato}";
	private static final String STATUS_NEXT = "/status/next/{idCandidato}";

	public Response cadastrarStatusPrevius(Integer idCandidato) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.pathParam("idCandidato", idCandidato)
				.when()
						.post(STATUS_PREVIUS);
	}

	public Response cadastrarStatusPreviusSemAutenticacao(Integer idCandidato) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.pathParam("idCandidato", idCandidato)
				.when()
						.post(STATUS_PREVIUS);
	}

	public Response cadastrarStatusNext(Integer idCandidato) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.header(AUTHORIZATION, AuthClient.getToken())
						.pathParam("idCandidato", idCandidato)
						.when()
						.post(STATUS_NEXT);
	}

	public Response cadastrarStatusNextSemAutenticacao(Integer idCandidato) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(setUp())
						.pathParam("idCandidato", idCandidato)
						.when()
						.post(STATUS_NEXT);
	}

}
