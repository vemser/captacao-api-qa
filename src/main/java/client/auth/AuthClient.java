package client.auth;

import lombok.Getter;
import lombok.Setter;
import models.login.LoginModel;
import specs.auth.AuthSpecs;

import static io.restassured.RestAssured.given;

public class AuthClient {
    private static final String LOGIN_ENDPOINT = "https://usuario-back.onrender.com/usuario/login";

    @Getter
    @Setter
    private static String token;

    @Getter
    @Setter
    private static String tokenInvalido;

    public AuthClient() {
    }

    public void logar(LoginModel loginModel) {
        String response =
                given()
                        .spec(AuthSpecs.authReqSpec())
                        .body(loginModel)
                        .when()
                        .post(LOGIN_ENDPOINT)
                        .then()
                        .extract()
                        .asString();

        setToken(response);
    }

    public void loginComOutroUsuario(LoginModel loginModel) {
        String response =
                given()
                        .spec(AuthSpecs.authReqSpec())
                        .body(loginModel)
                    .when()
                        .post(LOGIN_ENDPOINT)
                    .then()
                        .extract()
                        .asString();

        setToken(response);
    }
}
