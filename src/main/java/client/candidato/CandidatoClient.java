
package client.candidato;

import client.auth.AuthClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.linguagem.LinguagemClient;
import client.trilha.TrilhaClient;
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
    public static final String CANDIDATO_FINDBYEMAILS = "/candidato/findbyemails";
    public static final String CANDIDATO_ID_CANDIDATO = "/candidato/{idCandidato}";
    public static final String CANDIDATO_NOTA_PROVA_ID_CANDIDATO = "/candidato/nota-prova/{idCandidato}";
    public static final String CANDIDATO_DELETE_FISICO_ID_CANDIDATO = "/candidato/delete-fisico/{idCandidato}";
    public static final String CANDIDATO_DELETE_ID_CANDIDATO = "/candidato/{idCandidato}";
	public static final String CANDIDATO_ETAPA_NEXT = "/status/next/{idCandidato}";
	public static final String CANDIDATO_ETAPA_PREVIUS = "/status/previus/{idCandidato}";

    public static final String AUTHORIZATION = "Authorization";
    public static final String TAMANHO = "tamanho";
    public static final String ID_CANDIDATO = "idCandidato";
    public static final String LINGUAGEM_TESTE = "Java";

    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final LinguagemClient linguagemClient = new LinguagemClient();

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

    public Response deletarCandidato(Integer idCandidato) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                .when()
                        .delete(CANDIDATO_DELETE_ID_CANDIDATO);

    }

    public Response deletarCandidatoSemAutenticacao(Integer idCandidato) {
        Auth.usuarioAluno();
        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                .when()
                        .delete(CANDIDATO_DELETE_ID_CANDIDATO)
                ;
    }

    public Response deleteFisicoCandidato(Integer idCandidato) {
        Auth.usuarioGestaoDePessoas();

        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .when()
                        .delete(CANDIDATO_DELETE_FISICO_ID_CANDIDATO);

    }

	public Response avancarCandidatoEtapa(Integer idCandidato) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(CandidatoSpecs.candidatoReqSpec())
						.header(AUTHORIZATION, AuthClient.getToken())
						.pathParam(ID_CANDIDATO, idCandidato)
				.when()
							.post(CANDIDATO_ETAPA_NEXT);
	}

	public Response retrocederEtapaCandidato(Integer idCandidato) {
		Auth.usuarioGestaoDePessoas();

		return
				given()
						.spec(CandidatoSpecs.candidatoReqSpec())
						.header(AUTHORIZATION, AuthClient.getToken())
						.pathParam(ID_CANDIDATO, idCandidato)
				.when()
						.post(CANDIDATO_ETAPA_PREVIUS);
	}

    public Response deleteFisicoCandidatoSemAutenticacao(Integer idCandidato) {
        Auth.usuarioAluno();
        return
                given()
                        .spec(CandidatoSpecs.candidatoReqSpec())
                        .pathParam(ID_CANDIDATO, idCandidato)
                        .when()
                        .delete(CANDIDATO_DELETE_FISICO_ID_CANDIDATO)
                ;
    }
}
