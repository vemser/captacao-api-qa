package br.com.dbccompany.vemser.captacao.service;

import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FormularioService {

    public Response listar(){
        return
	            given()
                .when()
                        .get(Utils.getBaseUrl() + "/formulario/listar")
                ;
    }

    public Response listarComOrdenacao(String sort){
        return
                given()
                        .queryParam("sort", sort)
                .when()
                        .get(Utils.getBaseUrl() + "/formulario/listar")
                ;
    }

    public Response listarComPaginacao(Integer pagina, Integer tamanho, String sort){
        return
                given()
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("sort", sort)
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

    public Response cadastrar(String formulario) {
        return
                given()
                        .contentType(ContentType.JSON)
                        .body(formulario)
                .when()
                        .post(Utils.getBaseUrl() + "/formulario/cadastro")
                ;
    }

    public Response atualizarFormulario(Integer idFormulario, String formularioAtualizado) {
        return
                given()
                        .pathParam("idFormulario", idFormulario)
                        .contentType(ContentType.JSON)
                        .body(formularioAtualizado)
                .when()
                        .put(Utils.getBaseUrl() + "/formulario/atualizar-formulario/{idFormulario}")
                ;
    }

    public Response atualizarPrintConfigPc(Integer idFormulario) {
        return
                given()
                        .queryParam("idFormulario", idFormulario)
                        .multiPart(new File("./doc/imgPanda.jpg"))
                .when()
                        .put(Utils.getBaseUrl() + "/upload-print-config-pc")
                ;
    }

    public Response atualizarCurriculo(Integer idFormulario) {
        //RestAssured.defaultParser = Parser.JSON;
        return
                given()
                        .pathParam("idFormulario", idFormulario)
                        .contentType(ContentType.MULTIPART)
                        .multiPart(new File("./doc/curriculo.pdf"))
                .when()
                        .put(Utils.getBaseUrl() + "/formulario/upload-curriculo/{idFormulario}")
                ;
    }

    public Response deletarTeste(Integer idFormulario) {
        return
                given()
                        .pathParam("idFormulario", idFormulario)
                .when()
                        .delete(Utils.getBaseUrl() + "/formulario/delete-fisico/{idFormulario}")
                ;
    }

}
