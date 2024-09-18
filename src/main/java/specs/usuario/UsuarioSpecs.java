package specs.usuario;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import specs.InicialSpecs;

public class UsuarioSpecs extends InicialSpecs {

    public RequestSpecification usuarioSetUp(){
        return new RequestSpecBuilder()
                .addRequestSpecification(setUp())
                .setContentType(ContentType.JSON)
                .build();
    }
}
