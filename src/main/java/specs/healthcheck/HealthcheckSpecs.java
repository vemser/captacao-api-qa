package specs.healthcheck;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import specs.InicialSpecs;

public class HealthcheckSpecs {

    private HealthcheckSpecs() {
    }

    public static RequestSpecification healthcheckReqSpec() {
        return new RequestSpecBuilder()
            .addRequestSpecification(InicialSpecs.setUp())
            .setContentType(ContentType.JSON)
            .build();
    }

}
