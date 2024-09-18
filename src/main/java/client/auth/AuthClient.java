package client.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.login.LoginModel;
import specs.auth.AuthSpecs;

import static io.restassured.RestAssured.given;

@NoArgsConstructor
public class AuthClient {

    private static final String LOGIN_ENDPOINT = "https://usuario-back.onrender.com/usuario/login";

    @Getter
    @Setter
    private static String token;

    @Getter
    @Setter
    private static String tokenInvalido;



    public static String logar(LoginModel loginModel) {
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
        return response;
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
