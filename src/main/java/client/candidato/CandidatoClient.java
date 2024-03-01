package client.candidato;

import client.auth.AuthClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.linguagem.LinguagemClient;
import client.trilha.TrilhaClient;
import factory.candidato.CandidatoDataFactory;
import factory.formulario.FormularioDataFactory;
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
import specs.candidato.CandidatoSpecs;
import utils.auth.Auth;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CandidatoClient {

    public static final String CANDIDATO = "/candidato";
    public static final String CANDIDATO_FINDBYEMAILS = "/candidato/findbyemails";
    public static final String CANDIDATO_ID_CANDIDATO = "/candidato/{idCandidato}";
    public static final String CANDIDATO_ULTIMA_EDICAO = "/candidato/ultima-edicao";
    public static final String CANDIDATO_NOTA_PROVA_ID_CANDIDATO = "/candidato/nota-prova/{idCandidato}";
    public static final String CANDIDATO_NOTA_PARECER_TECNICO_ID_CANDIDATO = "/candidato/nota-parecer-tecnico/{idCandidato}";
    public static final String CANDIDATO_NOTA_COMPORTAMENTAL_ID_CANDIDATO = "/candidato/nota-comportamental/{idCandidato}";
    public static final String CANDIDATO_DELETE_FISICO_ID_CANDIDATO = "/candidato/delete-fisico/{idCandidato}";
    public static final String CANDIDATO_ATRIBUIR_NOTAS_EM_LOTE = "/candidato/atribuir-notas-em-lote";
    public static final String AUTHORIZATION = "Authorization";
    public static final String TAMANHO = "tamanho";
    public static final String EMAIL = "email";
    public static final String ID_CANDIDATO = "idCandidato";
    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final LinguagemClient linguagemClient = new LinguagemClient();

    public static final String LISTAR_TRILHAS = "/listarTrilhas";

    public Response listarTodosOsCandidatos() {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(CANDIDATO)
                ;
    }

    public Response listarTodosOsCandidatosSemAutenticacao() {

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                .when()
                        .get(CANDIDATO)
                ;
    }

    public Response listarNumCandidatos(Object numCandidatos) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(TAMANHO, numCandidatos)
                .when()
                        .get(CANDIDATO)
                ;
    }

    public Response listarCandidatoPorEmail(String email) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(EMAIL, email)
                .when()
                        .get(CANDIDATO_FINDBYEMAILS)
                ;
    }

    public Response cadastrarCandidato(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(CandidatoDataFactory.candidatoCriacaoValido(edicao, idFormulario, nomeLinguagem))
                .when()
                        .post(CANDIDATO)
                ;
    }

    public Response cadastrarCandidatoComCandidatoEntity(CandidatoCriacaoModel candidato) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(candidato)
                .when()
                        .post(CANDIDATO)
                ;
    }

    public Response atualizarCandidato(Integer idCandidato, CandidatoCriacaoModel novosDados) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(novosDados)
                .when()
                        .put(CANDIDATO_ID_CANDIDATO)
                ;
    }

    public Response atualizarCandidatoSemAutenticacao(Integer idCandidato, CandidatoCriacaoModel novosDados) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(novosDados)
                .when()
                        .put(CANDIDATO_ID_CANDIDATO)
                ;
    }

    public Response criarECadastrarCandidatoComCandidatoEntity() {
        Auth.usuarioGestaoDePessoas();

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        Response response =
                given()
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .body(candidatoCriado)
                        .when()
                        .post(CANDIDATO);

        return response;
    }


    public Response criarECadastrarCandidatoComCandidatoEntityETrilhaEspecifica(String nomeDaTrilha) {
        Auth.obterTokenComoAdmin();

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.stream().filter(trilha -> trilha.getNome().equals(nomeDaTrilha)).toList().get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(candidatoCriado)
                .when()
                        .post(CANDIDATO)
                ;
    }

    public Response criarECadastrarCandidatoComCandidatoEntityEEmailEspecifico(String emailCandidato) {
        Auth.obterTokenComoAdmin();

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                .toList();

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValidoComEmailEspecifico(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome(), emailCandidato);

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(candidatoCriado)
                .when()
                        .post(CANDIDATO)
                ;
    }

    public Response buscarCandidatoPorCpf(String cpf) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("cpf", cpf)
                .when()
                        .get(CANDIDATO_ULTIMA_EDICAO)
                ;
    }

    public Response buscarCandidatoPorCpfSemAutenticacao(String cpf) {

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .queryParam("cpf", cpf)
                .when()
                        .get(CANDIDATO_ULTIMA_EDICAO)
                ;
    }

    public Response atualizarNotaCandidato(Integer idCandidato, NotaModel nota) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(nota)
                .when()
                        .put(CANDIDATO_NOTA_PROVA_ID_CANDIDATO)
                ;
    }

    public Response atualizarNotaCandidatoSemAutenticacao(Integer idCandidato, NotaModel nota) {

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(nota)
                .when()
                        .put(CANDIDATO_NOTA_PROVA_ID_CANDIDATO)
                ;
    }

    public Response atualizarParecerTecnico(Integer idCandidato, ParecerTecnicoModel parecerTecnico) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(parecerTecnico)
                .when()
                        .put(CANDIDATO_NOTA_PARECER_TECNICO_ID_CANDIDATO)
                ;
    }

    public Response atualizarParecerTecnicoSemAutenticacao(Integer idCandidato, ParecerTecnicoModel parecerTecnico) {

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(parecerTecnico)
                .when()
                        .put(CANDIDATO_NOTA_PARECER_TECNICO_ID_CANDIDATO)
                ;
    }

    public Response atualizarParecerComportamental(Integer idCandidato, ParecerComportamentalModel parecerComportamental) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(parecerComportamental)
                .when()
                        .put(CANDIDATO_NOTA_COMPORTAMENTAL_ID_CANDIDATO)
                ;
    }

    public Response atualizarParecerComportamentalSemAutenticacao(Integer idCandidato, ParecerComportamentalModel parecerComportamental) {

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(parecerComportamental)
                .when()
                        .put(CANDIDATO_NOTA_COMPORTAMENTAL_ID_CANDIDATO)
                ;
    }

    public Response deletarCandidato(Integer idCandidato) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                .when()
                        .delete(CANDIDATO_DELETE_FISICO_ID_CANDIDATO);

    }

    public Response deletarCandidatoSemAutenticacao(Integer idCandidato) {

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                .when()
                        .delete(CANDIDATO_DELETE_FISICO_ID_CANDIDATO)
                ;
    }

    public Response atribuirNotasEmLote() {
        Auth.obterTokenComoAdmin();

        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\notas_em_lote.xlsx";
        File file = new File(filePath);

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .multiPart("file", file)
                .when()
                        .post(CANDIDATO_ATRIBUIR_NOTAS_EM_LOTE)
                ;
    }

    ///////// MÉTODOS DO BEFORE ALL
    public CandidatoCriacaoResponseModel cadastrarUsuarioBase(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        Auth.obterTokenComoAdmin();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(CandidatoDataFactory.candidatoCriacaoValido(edicao, idFormulario, nomeLinguagem))
                .when()
                        .post(CANDIDATO)
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .as(CandidatoCriacaoResponseModel.class)
                ;
    }
}
