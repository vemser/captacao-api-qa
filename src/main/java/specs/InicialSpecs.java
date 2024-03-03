package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;

public class InicialSpecs {
    // onRender
    //private static final String BASE_URI = "https://captacao-back-release.onrender.com";

    // VemSer Server
    //private static final String BASE_URI = "http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-back-release";
    private static final String BASE_URI = "http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-back";

    private InicialSpecs() {

    }

    public static RequestSpecification setUp() {
        return new RequestSpecBuilder()
                .setBaseUri(buildBaseUrl())
                .setConfig(config().logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .build();
    }

    private static String buildBaseUrl() {
        return BASE_URI;
    }
}
