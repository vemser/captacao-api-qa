package br.com.dbccompany.vemser.tests.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utils.Auth;

public abstract class BaseTest {

    private static Auth auth = new Auth();
    @BeforeAll
    public static void setUp() {

        //RestAssured.baseURI =  "http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-back";
        RestAssured.port = 8080;

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        auth.login();
    }

    @AfterAll
    public static void cleanUp() {

    }
}
