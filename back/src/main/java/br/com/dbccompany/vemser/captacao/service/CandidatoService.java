package br.com.dbccompany.vemser.captacao.service;

import br.com.dbccompany.vemser.captacao.utils.Utils;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
                        .contentType(ContentType.JSON)
                        .body(candidatoAtualizado)
                .when()
                        .put(Utils.getBaseUrl() + "/candidato/{idCandidato}")
                ;
    }

    public Response atualizarFoto(String email) {
        return
                given()
                        .pathParam("email", email)
                        .contentType(ContentType.MULTIPART)
                        .multiPart(new File("./doc/imgPanda.jpg"))
                .when()
                        .put(Utils.getBaseUrl() + "/candidato/upload-foto/{email}")
                ;
    }

    public Response atualizarNotaProva(Integer idCandidato) {
        Map<String, Number> notaProva = new HashMap<String, Number>();
        notaProva.put("notaProva", 60);
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(new Gson().toJson(notaProva))
                .when()
                        .put(Utils.getBaseUrl() + "/candidato/nota-prova/{idCandidato}")
                ;
    }

    public Response atualizarNotaProvaNegativa(Integer idCandidato) {
        Map<String, Number> notaProva = new HashMap<String, Number>();
        notaProva.put("notaProva", -1);
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(new Gson().toJson(notaProva))
                        .when()
                        .put(Utils.getBaseUrl() + "/candidato/nota-prova/{idCandidato}")
                ;
    }

    public Response atualizarNotaProvaMaior100(Integer idCandidato) {
        Map<String, Number> notaProva = new HashMap<String, Number>();
        notaProva.put("notaProva", 101);
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(new Gson().toJson(notaProva))
                        .when()
                        .put(Utils.getBaseUrl() + "/candidato/nota-prova/{idCandidato}")
                ;
    }

    public Response atualizarNotaParecerTecnico(Integer idCandidato, String nota) {
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(nota)
                        .when()
                        .put(Utils.getBaseUrl() + "/candidato/nota-parecer-tecnico/{idCandidato}")
                ;
    }

    public Response atualizarNotaComportamental(Integer idCandidato, String nota) {
        return
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(nota)
                        .when()
                        .put(Utils.getBaseUrl() + "/candidato/nota-comportamental/{idCandidato}")
                ;
    }

    public Response atualizarFotoInvalido(String email) {
        return
                given()
                        .pathParam("email", email)
                        .contentType(ContentType.MULTIPART)
                        .multiPart(new File("./doc/curriculo.doc"))
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
