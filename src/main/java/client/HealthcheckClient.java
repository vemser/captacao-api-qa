package client;

import io.restassured.response.Response;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class HealthcheckClient extends BaseClient {

    private static final String AUTHORIZATION = "Authorization";
    private static final String HEALTHCHECK = "/healthcheck";

    public Response healthcheck() {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(super.setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(HEALTHCHECK)
                ;
    }
}
