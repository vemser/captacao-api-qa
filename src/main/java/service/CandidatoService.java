package service;

import dataFactory.CandidatoDataFactory;
import dataFactory.FormularioDataFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.linguagem.LinguagemModel;
import models.nota.NotaModel;
import models.parecerComportamental.ParecerComportamentalModel;
import models.parecerTecnico.ParecerTecnicoModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import utils.Auth;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CandidatoService {

    private static CandidatoDataFactory candidatoDataFactory = new CandidatoDataFactory();
    private static TrilhaService trilhaService = new TrilhaService();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static FormularioService formularioService = new FormularioService();
    private static EdicaoService edicaoService = new EdicaoService();
    private static LinguagemService linguagemService = new LinguagemService();

    public Response listarTodosOsCandidatos() {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                .when()
                        .get("/candidato");

        return response;
    }

    public Response listarTodosOsCandidatosSemAutenticacao() {

        Response response =
                given()
                .when()
                        .get("/candidato");

        return response;
    }

    public Response listarNumCandidatos(Object numCandidatos) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("tamanho", numCandidatos)
                .when()
                        .get("/candidato");

        return response;
    }

    public Response listarCandidatoPorEmail(String email) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("email", email)
                .when()
                        .get("/candidato/findbyemails");

        return response;
    }

    public Response cadastrarCandidato(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(candidatoDataFactory.candidatoCriacaoValido(edicao, idFormulario, nomeLinguagem))
                .when()
                        .post("/candidato");

        return response;
    }

    public Response cadastrarCandidatoComCandidatoEntity(CandidatoCriacaoModel candidato) {

        Response response =
                given()
//                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(candidato)
                .when()
                        .post("/candidato");

        return response;
    }

    public Response atualizarCandidato(Integer idCandidato, CandidatoCriacaoModel novosDados) {

        Response response =
                given()
                        .headers("Authorization", Auth.getToken())
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(novosDados)
                .when()
                        .put("/candidato/{idCandidato}");

        return response;
    }

    public Response atualizarCandidatoSemAutenticacao(Integer idCandidato, CandidatoCriacaoModel novosDados) {

        Response response =
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(novosDados)
                .when()
                        .put("/candidato/{idCandidato}");

        return response;
    }

    public Response criarECadastrarCandidatoComCandidatoEntity() {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormularioComFormularioEntity(formulario);

        formularioService.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicaoCriada = edicaoService.criarEdicao();
        LinguagemModel linguagemCriada = linguagemService.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(candidatoCriado)
                .when()
                        .post("/candidato");

        return response;
    }

    public Response criarECadastrarCandidatoComCandidatoEntityETrilhaEspecifica(String nomeDaTrilha) {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.stream().filter(trilha -> trilha.getNome().equals(nomeDaTrilha)).toList().get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormularioComFormularioEntity(formulario);

        formularioService.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicaoCriada = edicaoService.criarEdicao();
        LinguagemModel linguagemCriada = linguagemService.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(candidatoCriado)
                        .when()
                        .post("/candidato");

        return response;
    }

    public Response criarECadastrarCandidatoComCandidatoEntityEEmailEspecifico(String emailCandidato) {

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaService.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = formularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioService.criarFormularioComFormularioEntity(formulario);

        formularioService.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicaoCriada = edicaoService.criarEdicao();
        LinguagemModel linguagemCriada = linguagemService.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = candidatoDataFactory.candidatoCriacaoValidoComEmailEspecifico(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome(), emailCandidato);

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(candidatoCriado)
                .when()
                        .post("/candidato");

        return response;
    }

    public Response buscarCandidatoPorCpf(String cpf) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .queryParam("cpf", cpf)
                .when()
                        .get("/candidato/ultima-edicao");

        return response;
    }

    public Response buscarCandidatoPorCpfSemAutenticacao(String cpf) {

        Response response =
                given()
                        .queryParam("cpf", cpf)
                .when()
                        .get("/candidato/ultima-edicao");

        return response;
    }

    public Response atualizarNotaCandidato(Integer idCandidato, NotaModel nota) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(nota)
                .when()
                        .put("/candidato/nota-prova/{idCandidato}");

        return response;
    }

    public Response atualizarNotaCandidatoSemAutenticacao(Integer idCandidato, NotaModel nota) {

        Response response =
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(nota)
                .when()
                        .put("/candidato/nota-prova/{idCandidato}");

        return response;
    }

    public Response atualizarParecerTecnico(Integer idCandidato, ParecerTecnicoModel parecerTecnico) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(parecerTecnico)
                .when()
                        .put("/candidato/nota-parecer-tecnico/{idCandidato}");

        return response;
    }

    public Response atualizarParecerTecnicoSemAutenticacao(Integer idCandidato, ParecerTecnicoModel parecerTecnico) {

        Response response =
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(parecerTecnico)
                .when()
                        .put("/candidato/nota-parecer-tecnico/{idCandidato}");

        return response;
    }

    public Response atualizarParecerComportamental(Integer idCandidato, ParecerComportamentalModel parecerComportamental) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(parecerComportamental)
                .when()
                        .put("/candidato/nota-comportamental/{idCandidato}");

        return response;
    }

    public Response atualizarParecerComportamentalSemAutenticacao(Integer idCandidato, ParecerComportamentalModel parecerComportamental) {

        Response response =
                given()
                        .pathParam("idCandidato", idCandidato)
                        .contentType(ContentType.JSON)
                        .body(parecerComportamental)
                .when()
                        .put("/candidato/nota-comportamental/{idCandidato}");

        return response;
    }

    public Response deletarCandidato(Integer idCandidato) {

        Response response =
                given()
                        .header("Authorization", Auth.getToken())
                        .pathParam("idCandidato", idCandidato)
                .when()
                        .delete("/candidato/delete-fisico/{idCandidato}");

        return response;
    }

    public Response deletarCandidatoSemAutenticacao(Integer idCandidato) {

        Response response =
                given()
                        .pathParam("idCandidato", idCandidato)
                .when()
                        .delete("/candidato/{idCandidato}");

        return response;
    }

    public Response atribuirNotasEmLote() {

        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\notas_em_lote.xlsx";
        File file = new File(filePath);

        var response =
                given()
                        .header("Authorization", Auth.getToken())
                        .multiPart("file", file)
                .when()
                        .post("/candidato/atribuir-notas-em-lote");

        return response;
    }

    ///////// MÉTODOS DO BEFORE ALL
    public CandidatoCriacaoResponseModel cadastrarUsuarioBase(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {

        CandidatoCriacaoResponseModel candidatoCriado =
                given()
                        .header("Authorization", Auth.getToken())
                        .contentType(ContentType.JSON)
                        .body(candidatoDataFactory.candidatoCriacaoValido(edicao, idFormulario, nomeLinguagem))
                .when()
                        .post("/candidato")
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .as(CandidatoCriacaoResponseModel.class);

        return candidatoCriado;
    }
}
