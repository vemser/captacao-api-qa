package specs.formulario;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import specs.InicialSpecs;

public class FormularioSpecs {
    private FormularioSpecs() {
    }

    public static RequestSpecification formularioReqSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setUp())
                .setContentType(ContentType.JSON)
                .build();
    }
}
