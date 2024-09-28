package client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.failure.JSONFailureResponseWithArrayModel;
import models.failure.JSONFailureResponseWithoutArrayModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import org.apache.http.HttpStatus;
import utils.auth.Auth;

import java.io.File;

import static io.restassured.RestAssured.given;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormularioClient extends BaseClient {
    private static final String AUTHORIZATION = "Authorization";
    public static final String FORMULARIO_LISTAR = "/formulario/listar";
    public static final String FORMULARIO_CADASTRO = "/formulario/cadastro";
    public static final String FORMULARIO_UPLOAD_CURRICULO_ID_FORMULARIO = "/formulario/upload-curriculo/{idFormulario}";
    public static final String FORMULARIO_UPLOAD_PRINT_CONFIG_PC_ID_FORMULARIO = "/formulario/upload-print-config-pc/{idFormulario}";
    public static final String FORMULARIO_UPLOAD_COMP_MATRICULA = "/formulario/upload-comp-matricula/{idFormulario}";
    public static final String FORMULARIO_ATUALIZAR_FORMULARIO_ID_FORMULARIO = "/formulario/atualizar-formulario/{idFormulario}";
    public static final String FORMULARIO_DELETE_FISICO_ID_FORMULARIO = "/formulario/delete-fisico/{idFormulario}";
    public static final String FORMULARIO_BUSCAR_COMP_MATRICULA = "/formulario/recuperar-comp-matricula";
    public static final String FORMULARIO_BUSCAR_CONFIG_PC = "/formulario/recuperar-print-config-pc";
    public static final String FORMULARIO_BUSCAR_CURRICULO = "/formulario/recuperar-curriculo";
    public static final String SORT = "sort";
    public static final String ORDER = "order";
    public static final String ID_FORMULARIO = "idFormulario";
    public static final String FILE = "file";

    public Response listarTodosOsFormularios() {
        Auth.usuarioGestaoDePessoas();
        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(FORMULARIO_LISTAR)
                ;
    }

    public Response listarFormulariosSemAutenticacao() {

        return
                given()
                        .spec(setUp())
                .when()
                        .get(FORMULARIO_LISTAR)
                ;
    }

    public Response recuperarCompMatricula(Integer idFormulario) {
        Auth.usuarioGestaoDePessoas();
        return
                given()
                        .spec(setUp())
                        .queryParam(ID_FORMULARIO, idFormulario)
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(FORMULARIO_BUSCAR_COMP_MATRICULA)
                ;
    }

    public Response recuperarPrintConfigPc(Integer idFormulario) {
        Auth.usuarioGestaoDePessoas();
        return
                given()
                        .spec(setUp())
                        .queryParam(ID_FORMULARIO, idFormulario)
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(FORMULARIO_BUSCAR_CONFIG_PC)
                ;
    }

    public Response recuperarCurriculo(Integer idFormulario) {
        Auth.usuarioGestaoDePessoas();
        return
                given()
                        .spec(setUp())
                        .queryParam(ID_FORMULARIO, idFormulario)
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(FORMULARIO_BUSCAR_CURRICULO)
                ;
    }

    public Response listarNumDeFormulariosOrdemDecrescente() {
        Auth.usuarioGestaoDePessoas();

        Integer ordemDecrescente = 1;

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(ORDER, ordemDecrescente)
                        .queryParam(SORT, ID_FORMULARIO)
                .when()
                        .get(FORMULARIO_LISTAR)
                ;
    }


    public Response criarFormularioContrato(FormularioCriacaoModel formulario) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .contentType(ContentType.JSON)
                        .body(formulario)
                .when()
                        .post(FORMULARIO_CADASTRO);

    }

    public FormularioCriacaoResponseModel criarFormularioComFormularioEntity(FormularioCriacaoModel formulario) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .contentType(ContentType.JSON)
                        .body(formulario)
                .when()
                        .post(FORMULARIO_CADASTRO)
                .then()
                        .extract()
                        .as(FormularioCriacaoResponseModel.class)
                ;
    }

    public JSONFailureResponseWithoutArrayModel criarFormularioDadoInvalido(FormularioCriacaoModel formulario) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .contentType(ContentType.JSON)
                        .body(formulario)
                .when()
                        .post(FORMULARIO_CADASTRO)
                .then()
                        .extract()
                        .as(JSONFailureResponseWithoutArrayModel.class)
                ;
    }

    public JSONFailureResponseWithArrayModel criarFormularioInstituicaoNula(FormularioCriacaoModel formulario) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .contentType(ContentType.JSON)
                        .body(formulario)
                .when()
                        .post(FORMULARIO_CADASTRO)
                .then()
                        .extract()
                        .as(JSONFailureResponseWithArrayModel.class)
                ;
    }

    public JSONFailureResponseWithArrayModel criarFormularioRespostaVazia(FormularioCriacaoModel formulario) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .contentType(ContentType.JSON)
.body(formulario)
                .when()
                        .post(FORMULARIO_CADASTRO)
                .then()
                        .extract()
                        .as(JSONFailureResponseWithArrayModel.class)
                ;
    }

    public Response incluiCurriculoEmFormularioComValidacao(Integer idFormulario) {
        Auth.usuarioGestaoDePessoas();

        String filePath = "src/test/resources/curriculo_em_pdf.pdf";
        File file = new File(filePath);

        return
                given()
                        .spec(setUp())
                        .contentType("multipart/form-data")
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .multiPart(FILE, file)
                .when()
                        .put(FORMULARIO_UPLOAD_CURRICULO_ID_FORMULARIO);
    }

    public Response incluiCurriculoEmFormularioSemValidacao(Integer idFormulario) {
        Auth.usuarioAluno();

        String filePath = "src/test/resources/curriculo_em_pdf.pdf";
        File file = new File(filePath);

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .contentType("multipart/form-data")
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .multiPart(FILE, file)
                .when()
                        .put(FORMULARIO_UPLOAD_CURRICULO_ID_FORMULARIO);
    }

    public Response incluiConfigPcEmFormularioSemValidacao(Integer idFormulario) {

        String filePath = "src/test/resources/config_pc.png";
        File file = new File(filePath);

        return
                given()
                        .spec(setUp())
                        .contentType("multipart/form-data")
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .multiPart(FILE, file)
                .when()
                        .put(FORMULARIO_UPLOAD_PRINT_CONFIG_PC_ID_FORMULARIO);
    }

    public Response incluiConfigPcEmFormularioComValidacao(Integer idFormulario) {
        Auth.usuarioGestaoDePessoas();

        String filePath = "src/test/resources/config_pc.png";
        File file = new File(filePath);

        return
                given()
                        .spec(setUp())
                        .contentType("multipart/form-data")
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .multiPart(FILE, file)
                .when()
                        .put(FORMULARIO_UPLOAD_PRINT_CONFIG_PC_ID_FORMULARIO);
    }

    public Response incluiComprovanteMatriculaComValidacao(Integer idFormulario) {
        Auth.usuarioGestaoDePessoas();

        String filePath = "src/test/resources/comprov_matricula_teste.pdf";
        File file = new File(filePath);

        return
                given()
                        .spec(setUp())
                        .contentType("multipart/form-data")
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .multiPart(FILE, file)
                .when()
                        .put(FORMULARIO_UPLOAD_COMP_MATRICULA)
                ;
    }

    public Response atualizaFormularioContrato(Integer idFormulario, FormularioCriacaoModel formularioAtualizado) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .contentType(ContentType.JSON)
                        .body(formularioAtualizado)
                .when()
                        .put(FORMULARIO_ATUALIZAR_FORMULARIO_ID_FORMULARIO);
    }

    public FormularioCriacaoResponseModel atualizaFormulario(Integer idFormulario, FormularioCriacaoModel formularioAtualizado) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .contentType(ContentType.JSON)
                        .body(formularioAtualizado)
                .when()
                        .put(FORMULARIO_ATUALIZAR_FORMULARIO_ID_FORMULARIO)
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(FormularioCriacaoResponseModel.class);
    }

    public Response deletarFormulario(Integer idFormulario) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(setUp())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                .when()
                        .delete(FORMULARIO_DELETE_FISICO_ID_FORMULARIO);
    }
}
