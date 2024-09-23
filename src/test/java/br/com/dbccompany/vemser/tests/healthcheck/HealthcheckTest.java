package br.com.dbccompany.vemser.tests.healthcheck;

import client.healthcheck.HealthcheckClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de healthcheck")
public class HealthcheckTest {

    private static HealthcheckClient healthcheckClient = new HealthcheckClient();

    @Test
    @DisplayName("Cenário 1: Healthcheck API")
    @Tag("Regression")
    public void testValidarHealthcheck() {

        healthcheckClient.healthcheck()
                .then()
                .statusCode(200);
        ;
    }
}
