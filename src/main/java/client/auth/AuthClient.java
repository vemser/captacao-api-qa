package client.auth;

import lombok.Getter;
import lombok.Setter;
import models.login.LoginModel;
import specs.auth.AuthSpecs;

import static io.restassured.RestAssured.given;

public class AuthClient {
    private static final String LOGIN_ENDPOINT = "https://usuario-back.onrender.com/usuario-controller/login";

    @Getter
    @Setter
    private static String token;

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

    public void loginInvalido(LoginModel loginModel) {
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
