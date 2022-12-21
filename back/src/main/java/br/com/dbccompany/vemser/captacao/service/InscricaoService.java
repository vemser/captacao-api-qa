package br.com.dbccompany.vemser.captacao.service;

import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class InscricaoService {

    public Response inscrever() {
        return
                given()
                .when()
                        .post(Utils.getBaseUrl() + "/subscription")
                ;
    }

}
