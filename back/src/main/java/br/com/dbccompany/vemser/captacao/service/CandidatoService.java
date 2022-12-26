package br.com.dbccompany.vemser.captacao.service;

import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class CandidatoService {

    public Response buscarListaPorId(Integer idCandidato){
        return
	            given()
                        .queryParam("sort", idCandidato)
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

    public Response cadastroCandidato(String candidato) {
        return
                given()
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
                        .put(Utils.getBaseUrl() + "/candidato/upload-foto/")
                ;
    }

    public Response deletarTeste(Integer idCandidato) {
        return
                given()
                        .pathParam("idFormulario", idCandidato)
                .when()
                        .delete(Utils.getBaseUrl() + "/candidato/")
                ;
    }

    public Response deletarTesteFisico(Integer idCandidato) {
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                .when()
                        .delete(Utils.getBaseUrl() + "/candidato/delete-fisico/")
                ;
    }
}
