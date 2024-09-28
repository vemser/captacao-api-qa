package client;

import io.restassured.http.ContentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.login.LoginModel;

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



    public void logar(LoginModel loginModel) {
        String response =
                given()
                        .body(loginModel)
                        .contentType(ContentType.JSON)
                .when()
                        .post(LOGIN_ENDPOINT)
                        .then()
                        .extract()
                        .asString();

        setToken(response);
    }

}
