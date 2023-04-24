package service;

import dataFactory.FormularioDataFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.formulario.JSONListaFormularioResponse;
import org.apache.http.HttpStatus;
import utils.Auth;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FormularioService {

    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();

    public Response listarTodosOsFormularios() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/formulario/listar");

        return response;
    }

    public Response listarNumDeFormularios(Integer numDeFormularios) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("tamanho", numDeFormularios)
                .when()
                        .get("/formulario/listar");

        return response;
    }

    public Response listarNumDeFormulariosOrdemDecrescente(Integer numDeFormulario) {
        Integer ordemDecrescente = 1;

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("tamanho", numDeFormulario)
                        .queryParam("order", ordemDecrescente)
                .when()
                        .get("/formulario/listar");

        return response;
    }

    public FormularioCriacaoResponseModel criarFormulario(String nomeDeTrilhaExistente) {

        FormularioCriacaoResponseModel formularioCriado =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(formularioDataFactory.formularioValidoComTrilhaExistente(nomeDeTrilhaExistente))
                .when()
                        .post("/formulario/cadastro")
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(FormularioCriacaoResponseModel.class);

        return formularioCriado;
    }

    public FormularioCriacaoResponseModel criarFormularioComFormularioEntity(FormularioCriacaoModel formulario) {

        FormularioCriacaoResponseModel formularioCriado =
                given()
//                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(formulario)
                .when()
                        .post("/formulario/cadastro")
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(FormularioCriacaoResponseModel.class);

        return formularioCriado;
    }

    public void incluiCurriculoEmFormularioComValidacao(Integer idFormulario) {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\curriculo_em_pdf.pdf";
        File file = new File(filePath);

        var response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idFormulario", idFormulario)
                        .multiPart("file", file)
                .when()
                        .put("/formulario/upload-curriculo/{idFormulario}")
                .then()
                        .statusCode(HttpStatus.SC_OK);
    }

    public Response incluiCurriculoEmFormularioSemValidacao(Integer idFormulario) {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\curriculo_em_pdf.pdf";
        File file = new File(filePath);

        var response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idFormulario", idFormulario)
                        .multiPart("file", file)
                .when()
                        .put("/formulario/upload-curriculo/{idFormulario}");

        return response;
    }

    public Response incluiConfigPcEmFormularioSemValidacao(Integer idFormulario) {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\config_pc.png";
        File file = new File(filePath);

        var response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idFormulario", idFormulario)
                        .multiPart("file", file)
                .when()
                        .put("/formulario/upload-print-config-pc/{idFormulario}");

        return response;
    }

    public FormularioCriacaoResponseModel atualizaFormulario(Integer idFormulario, FormularioCriacaoModel formularioAtualizado) {

        FormularioCriacaoResponseModel formulario =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idFormulario", idFormulario)
                        .contentType(ContentType.JSON)
                        .body(formularioAtualizado)
                .when()
                        .put("/formulario/atualizar-formulario/{idFormulario}")
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(FormularioCriacaoResponseModel.class);

        return formulario;
    }

    public Response atualizaFormularioSemAutenticacao(Integer idFormulario, FormularioCriacaoModel formularioAtualizado) {

        Response response =
                given()
                        .pathParam("idFormulario", idFormulario)
                        .contentType(ContentType.JSON)
                        .body(formularioAtualizado)
                .when()
                        .put("/formulario/atualizar-formulario/{idFormulario}");

        return response;
    }

    public Response deletarFormulario(Integer idFormulario) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idFormulario", idFormulario)
                .when()
                        .delete("/formulario/delete-fisico/{idFormulario}");

        return response;
    }

    public Response deletarFormularioSemAutenticacao(Integer idFormulario) {

        Response response =
                given()
                        .pathParam("idFormulario", idFormulario)
                        .when()
                        .delete("/formulario/delete-fisico/{idFormulario}");

        return response;
    }

    public Boolean verificaSeExistemFormulariosCadastrados() {

        var response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/formulario/listar")
                .then()
                        //.statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(JSONListaFormularioResponse.class);

        if (response.getElementos().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
