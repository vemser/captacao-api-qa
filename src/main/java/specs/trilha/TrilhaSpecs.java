package specs.trilha;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import specs.InicialSpecs;

public class TrilhaSpecs {
    private TrilhaSpecs() {
    }

    public static RequestSpecification trilhaReqSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setUp())
                .setContentType(ContentType.JSON)
                .build();
    }
}
