package br.com.dbccompany.vemser.captacao.service;

import br.com.dbccompany.vemser.captacao.utils.Utils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class InscricaoService {

    public Response cadastroInscricao(Integer idCandidato) {
        return
                given()
                        .queryParam("idCandidato", idCandidato)
                .when()
                .post(Utils.getBaseUrl() + "/inscricao/cadastro")
                ;
    }

    public Response buscarInscricao(){
        return
                given()
                        .when()
                        .get(Utils.getBaseUrl() + "/inscricao")
                ;
    }

    public Response buscarInscricaoPorTrilha(String trilha){
        return
                given()
                        .queryParam("trilha", trilha)
                        .when()
                        .get(Utils.getBaseUrl() + "/inscricao/list-by-trilha")
                ;
    }

    public Response buscarInscricaoPorEdicao(String edicao){
        return
                given()
                        .queryParam("edicao", edicao)
                        .when()
                        .get(Utils.getBaseUrl() + "/inscricao/list-by-edicao")
                ;
    }

    public Response buscarInscricaoPorEmail(String email){
        return
                given()
                        .queryParam("email", email)
                        .when()
                        .get(Utils.getBaseUrl() + "/inscricao/list-by-email")
                ;
    }

    public Response buscarInscricaoPorId(Integer idInscricao){
        return
                given()
                        .queryParam("id", idInscricao)
                        .when()
                        .get(Utils.getBaseUrl() + "/inscricao/by-id")
                ;
    }

    public Response deletarInscricao(Integer idInscricao) {
        return
                given()
                        .pathParam("idInscricao", idInscricao)
                        .when()
                        .delete(Utils.getBaseUrl() + "/inscricao/{idInscricao}")
                ;
    }
}
