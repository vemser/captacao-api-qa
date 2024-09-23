
package client.candidato;

import client.auth.AuthClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import factory.candidato.CandidatoDataFactory;
import factory.edicao.EdicaoDataFactory;
import factory.formulario.FormularioDataFactory;
import io.restassured.response.Response;
import models.candidato.CandidatoCriacaoModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import specs.candidato.CandidatoSpecs;
import utils.auth.Auth;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CandidatoClient {

    public static final String CANDIDATO = "/candidato";
    public static final String CANDIDATO_ID_CANDIDATO = "/candidato/{idCandidato}";
    public static final String CANDIDATO_DELETE_FISICO_ID_CANDIDATO = "/candidato/delete-fisico/{idCandidato}";

    public static final String AUTHORIZATION = "Authorization";
    public static final String TAMANHO = "tamanho";
    public static final String ID_CANDIDATO = "idCandidato";
    public static final String LINGUAGEM_TESTE = "Java";

    private static final FormularioClient formularioClient = new FormularioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();

    public Response listarTodosOsCandidatos(Integer pagina, Integer tamanho) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("pagina", pagina)
                        .queryParam("tamanho", tamanho)
                        .queryParam("sort", "idCandidato")
                .when()
                        .get(CANDIDATO)
                ;
    }

    public Response listarNumCandidatos(Object numCandidatos) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam(TAMANHO, numCandidatos)
                .when()
                        .get(CANDIDATO)
                ;
    }

    public Response cadastrarCandidatoComCandidatoEntity(CandidatoCriacaoModel candidato) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .contentType("application/json")
                        .body(candidato)
                .when()
                        .post(CANDIDATO)
                ;
    }

    public Response atualizarCandidato(Integer idCandidato, CandidatoCriacaoModel novosDados) {
        Auth.usuarioGestaoDePessoas();

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
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .body(novosDados)
                .when()
                        .put(CANDIDATO_ID_CANDIDATO)
                ;
    }

    public Response criarECadastrarCandidatoComCandidatoEntity() {
        Auth.usuarioGestaoDePessoas();

        List<String> listaDeNomeDeTrilhas = new ArrayList<>();

        listaDeNomeDeTrilhas.add("FRONTEND");

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        formularioClient.incluiCurriculoEmFormularioComValidacao(formularioCriado.getIdFormulario());

        EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao(edicao);

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), LINGUAGEM_TESTE);

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
        Auth.usuarioGestaoDePessoas();

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

        EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao(edicao);
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

        EdicaoModel edicao = EdicaoDataFactory.edicaoValida();

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao(edicao);

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

    public Response buscarCandidatoPorEmail(String email) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("email", email)
                .when()
                        .get(CANDIDATO_FINDBYEMAILS)
                ;
    }

    public Response buscarCandidatoPorEmailSemAutenticacao(String email) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .queryParam("email", email)
                .when()
                        .get(CANDIDATO_FINDBYEMAILS)
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

    public Response atualizarParecerTecnico(Integer idCandidato, ParecerTecnicoModel parecerTecnico) {
        Auth.usuarioGestaoDePessoas();

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
        Auth.usuarioAluno();
        return
                given()
                        .header(AUTHORIZATION, AuthClient.getToken())
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
        Auth.usuarioAluno();

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

    public Response deletarCandidato(Integer idCandidato) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                .when()
                        .delete(CANDIDATO_DELETE_FISICO_ID_CANDIDATO);

    }

}
