package br.com.dbccompany.vemser.captacao.aceitacao.base;

import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = Utils.getBaseUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}
