package br.com.dbccompany.vemser.captacao.service;

import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;
public class CandidatoService {

    public Response buscarListaPorId(){
        return
	            given()
                .when()
                        .get(Utils.getBaseUrl() + "/candidato")
                ;
    }

    public Response buscarImagemPorEmail(String email){
        return
	            given()
                        .queryParam("email", email)
                .when()
                        .get(Utils.getBaseUrl() + "/candidato/recuperar-imagem")
                ;
    }

    public Response buscarCandidatoPorEmail(String email){
        return
	            given()
                        .queryParam("email", email)
                .when()
                        .get(Utils.getBaseUrl() + "/candidato/findbyemails")
                ;
    }

    public Response buscarCandidatosPorTrilha(String trilha){
        return
                given()
                        .queryParam("trilha", trilha)
                        .when()
                        .get(Utils.getBaseUrl() + "/candidato/find-by-trilha")
                ;
    }

    public Response buscarCandidatosPorEdicao(String edicao){
        return
                given()
                        .queryParam("edicao", edicao)
                        .when()
                        .get(Utils.getBaseUrl() + "/candidato/find-by-edicao")
                ;
    }

    public Response cadastroCandidato(String candidato) {
        return
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .body(candidato)
                .when()
                        .post(Utils.getBaseUrl() + "/candidato")
                ;
    }

    public Response atualizarCandidato(Integer idCandidato, String candidatoAtualizado) {
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                        .body(candidatoAtualizado)
                .when()
                        .put(Utils.getBaseUrl() + "/candidato/")
                ;
    }

    public Response atualizarFoto(String email) {
        return
                given()
                        .pathParam("email", email)
                        .multiPart(new File("./doc/imgPanda.jpg"))
                .when()
                        .put(Utils.getBaseUrl() + "/candidato/upload-foto/{email}")
                ;
    }

    public Response atualizarNotaProva(String email) {
        return
                given()
                        .pathParam("email", email)
                        .multiPart(new File("./doc/imgPanda.jpg"))
                        .when()
                        .put(Utils.getBaseUrl() + "/candidato/upload-foto/{email}")
                ;
    }

    public Response deletarTeste(Integer idCandidato) {
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                .when()
                        .delete(Utils.getBaseUrl() + "/candidato/{idCandidato}")
                ;
    }

    public Response deletarTesteFisico(Integer idCandidato) {
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                .when()
                        .delete(Utils.getBaseUrl() + "/candidato/delete-fisico/{idCandidato}")
                ;
    }
}
