package specs.inscricao;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import specs.InicialSpecs;

public class InscricaoSpecs {
    private InscricaoSpecs() {
    }

    public static RequestSpecification authReqSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setUp())
                .setContentType(ContentType.JSON)
                .build();
    }
}
