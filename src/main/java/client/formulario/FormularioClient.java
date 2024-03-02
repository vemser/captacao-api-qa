package client.formulario;

import client.auth.AuthClient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import factory.formulario.FormularioDataFactory;
import io.restassured.response.Response;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.formulario.JSONListaFormularioResponse;
import org.apache.http.HttpStatus;
import specs.formulario.FormularioSpecs;
import utils.auth.Auth;

import java.io.File;

import static io.restassured.RestAssured.given;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormularioClient {
    private static final String AUTHORIZATION = "Authorization";
    public static final String FORMULARIO_LISTAR = "/formulario/listar";
    public static final String FORMULARIO_CADASTRO = "/formulario/cadastro";
    public static final String FORMULARIO_UPLOAD_CURRICULO_ID_FORMULARIO = "/formulario/upload-curriculo/{idFormulario}";
    public static final String FORMULARIO_UPLOAD_PRINT_CONFIG_PC_ID_FORMULARIO = "/formulario/upload-print-config-pc/{idFormulario}";
    public static final String FORMULARIO_ATUALIZAR_FORMULARIO_ID_FORMULARIO = "/formulario/atualizar-formulario/{idFormulario}";
    public static final String FORMULARIO_DELETE_FISICO_ID_FORMULARIO = "/formulario/delete-fisico/{idFormulario}";
    public static final String TAMANHO = "tamanho";
    public static final String ORDER = "order";
    public static final String ID_FORMULARIO = "idFormulario";
    public static final String FILE = "file";


    public Response listarTodosOsFormularios() {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(FORMULARIO_LISTAR)
                ;
    }

    public Response listarNumDeFormularios(Integer numDeFormularios) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(TAMANHO, numDeFormularios)
                .when()
                        .get(FORMULARIO_LISTAR)
                ;
    }

    public Response listarNumDeFormulariosOrdemDecrescente(Integer numDeFormulario) {
        Auth.obterTokenComoAdmin();

        Integer ordemDecrescente = 1;

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(TAMANHO, numDeFormulario)
                        .queryParam(ORDER, ordemDecrescente)
                .when()
                        .get(FORMULARIO_LISTAR)
                ;
    }

    public FormularioCriacaoResponseModel criarFormulario(String nomeDeTrilhaExistente) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(FormularioDataFactory.formularioValidoComTrilhaExistente(nomeDeTrilhaExistente))
                .when()
                        .post(FORMULARIO_CADASTRO)
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .as(FormularioCriacaoResponseModel.class)
                ;
    }

    public FormularioCriacaoResponseModel criarFormularioComFormularioEntity(FormularioCriacaoModel formulario) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(formulario)
                .when()
                        .post(FORMULARIO_CADASTRO)
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .as(FormularioCriacaoResponseModel.class)
                ;
    }

    public void incluiCurriculoEmFormularioComValidacao(Integer idFormulario) {
        Auth.obterTokenComoAdmin();

        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\curriculo_em_pdf.pdf";
        File file = new File(filePath);

        given()
                .spec(FormularioSpecs.formularioReqSpec())
                .contentType("multipart/form-data")
                .header(AUTHORIZATION, AuthClient.getToken())
                .pathParam(ID_FORMULARIO, idFormulario)
                .multiPart(FILE, file)
            .when()
                .put(FORMULARIO_UPLOAD_CURRICULO_ID_FORMULARIO);
    }


    public Response incluiCurriculoEmFormularioSemValidacao(Integer idFormulario) {
        Auth.usuarioAluno();
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\curriculo_em_pdf.pdf";
        File file = new File(filePath);

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .contentType("multipart/form-data")
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .multiPart(FILE, file)
                .when()
                        .put(FORMULARIO_UPLOAD_CURRICULO_ID_FORMULARIO);
    }


    public Response incluiConfigPcEmFormularioSemValidacao(Integer idFormulario) {
        Auth.usuarioGestaoDePessoas();

        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\config_pc.png";
        File file = new File(filePath);

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .contentType("multipart/form-data")
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .multiPart(FILE, file)
                .when()
                        .put(FORMULARIO_UPLOAD_PRINT_CONFIG_PC_ID_FORMULARIO);
    }

    public FormularioCriacaoResponseModel atualizaFormulario(Integer idFormulario, FormularioCriacaoModel formularioAtualizado) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .body(formularioAtualizado)
                .when()
                        .put(FORMULARIO_ATUALIZAR_FORMULARIO_ID_FORMULARIO)
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(FormularioCriacaoResponseModel.class);
    }

    public Response atualizaFormularioSemAutenticacao(Integer idFormulario, FormularioCriacaoModel formularioAtualizado) {

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .pathParam(ID_FORMULARIO, idFormulario)
                        .body(formularioAtualizado)
                .when()
                        .put(FORMULARIO_ATUALIZAR_FORMULARIO_ID_FORMULARIO);
    }

    public Response deletarFormulario(Integer idFormulario) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                .when()
                        .delete(FORMULARIO_DELETE_FISICO_ID_FORMULARIO);
    }

    public Response deletarFormularioSemAutenticacao(Integer idFormulario) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_FORMULARIO, idFormulario)
                .when()
                        .delete(FORMULARIO_DELETE_FISICO_ID_FORMULARIO);
    }

    public Boolean verificaSeExistemFormulariosCadastrados() {
        Auth.obterTokenComoAdmin();

        var response =
                given()
                        .spec(FormularioSpecs.formularioReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(FORMULARIO_LISTAR)
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(JSONListaFormularioResponse.class);

        return ! response.getElementos().isEmpty();
    }
}
