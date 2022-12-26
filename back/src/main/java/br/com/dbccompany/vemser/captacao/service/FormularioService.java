package br.com.dbccompany.vemser.captacao.service;

import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FormularioService {

    public Response buscarListaPorId(Integer idFormulario){
        return
	            given()
                        .queryParam("sort", idFormulario)
                .when()
                        .get(Utils.getBaseUrl() + "/formulario/listar")
                ;
    }

    public Response buscarCurriculoPorIdFormulario(Integer idFormulario){
        return
	            given()
                        .queryParam("idFormulario", idFormulario)
                .when()
                        .get(Utils.getBaseUrl() + "/formulario/recuperar-curriculo")
                ;
    }

    public Response cadastroFormulario(String formulario) {
        return
                given()
                        .body(formulario)
                .when()
                        .post(Utils.getBaseUrl() + "/formulario/cadastro")
                ;
    }

    public Response atualizarFormulario(Integer idFormulario, String formularioAtualizado) {
        return
                given()
                        .pathParam("idFormulario", idFormulario)
                        .body(formularioAtualizado)
                .when()
                        .put(Utils.getBaseUrl() + "/formulario/")
                ;
    }

    public Response atualizarPrintConfigPc(Integer idFormulario) {
        return
                given()
                        .pathParam("idFormulario", idFormulario)
                        .multiPart(new File("./doc/imgPanda.jpg"))
                .when()
                        .put(Utils.getBaseUrl() + "/upload-print-config-pc/")
                ;
    }

    public Response atualizarCurriculo(Integer idFormulario) {
        return
                given()
                        .pathParam("idFormulario", idFormulario)
                        .multiPart(new File("./doc/curriculo.pdf"))
                .when()
                        .put(Utils.getBaseUrl() + "/upload-curriculo/")
                ;
    }

    public Response deletarTeste(Integer idFormulario) {
        return
                given()
                        .pathParam("idFormulario", idFormulario)
                .when()
                        .delete(Utils.getBaseUrl() + "/formulario/")
                ;
    }
}
