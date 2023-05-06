package utils;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class Auth {

    private static String token;
    private static AdmLoginData admLoginData = new AdmLoginData();
    public static String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public void login() {

        String response =
                given()
                        .contentType(ContentType.JSON)
                        .body(admLoginData)
                .when()
                        .post("http://vemser-dbc.dbccompany.com.br:39000/vemser/usuario-back/usuario/login")
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .asString();

        this.setToken(response);
    }
}
