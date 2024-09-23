package client.healthcheck;

import client.auth.AuthClient;
import io.restassured.response.Response;
import specs.healthcheck.HealthcheckSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class HealthcheckClient {

    private static final String AUTHORIZATION = "Authorization";
    private static final String HEALTHCHECK = "/healthcheck";

    public Response healthcheck() {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(HealthcheckSpecs.healthcheckReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(HEALTHCHECK)
                ;
    }
}
