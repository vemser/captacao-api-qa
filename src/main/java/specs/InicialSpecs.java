package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;

public class InicialSpecs {

    // VemSer Server
    private static final String BASE_URI = "https://captacao-back-hml.onrender.com/";


    public InicialSpecs() {

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
