package client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;

public class BaseClient {

    // VemSer Server
    private static final String BASE_URI = "https://captacao-back-hml-yx8y.onrender.com/";

    public BaseClient() {
    }

    public static RequestSpecification setUp() {
        RestAssured.defaultParser = Parser.JSON;
        return new RequestSpecBuilder()
                .setBaseUri(buildBaseUrl())
                .setConfig(config().logConfig(
                        LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build();
    }

    private static String buildBaseUrl() {
        return BASE_URI;
    }
}
