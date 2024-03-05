package client.questao;

import client.auth.AuthClient;
import io.restassured.response.Response;
import specs.questao.QuestaoSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class QuestaoClient {
    private static final String AUTHORIZATION = "Authorization";
    private static final String LISTAR_QUESTOES = "/questao";
    private static final String POR_TIPO = "/questao/por-tipo";
    private static final String POR_DIFICULDADE = "/questao/por-dificuldade";
    private static final String POR_PALAVRA_CHAVE = "/questao/por-palavra-chave";
    private static final String LISTAR_QUESTOES_ALEATORIAS = "/questao/listar-questoes-aleatorias";
    private static final String DELETAR_QUESTAO = "/questao/{idQuestao}";

    public Response listarTodasAsQuestoes() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                .when()
                        .get(LISTAR_QUESTOES)
                ;
    }

    public Response listarQuestoesPorTipo(String tipo) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("tipo", tipo)
                .when()
                        .get(POR_TIPO)
                ;
    }

    public Response listarQuestoesPorPalavraChave(String keyword) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("keyword", keyword)
                .when()
                        .get(POR_PALAVRA_CHAVE)
                ;
    }

    public Response listarQuestoesPorDificuldade(String dificuldade) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("dificuldade", dificuldade)
                .when()
                        .get(POR_DIFICULDADE)
                ;
    }

    public Response listarQuestoesAleatorias(String dificuldade, Integer numero) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .queryParam("dificuldade", dificuldade)
                        .queryParam("numeroQuestoes", numero)
                .when()
                        .get(LISTAR_QUESTOES_ALEATORIAS)
                ;
    }

    public Response deletarQuestao(Integer id) {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idQuestao", id)
                .when()
                        .delete(DELETAR_QUESTAO)
                ;
    }

    public Response deletarQuestaoSemAutenticacao(Integer id) {
        Auth.usuarioAluno();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idQuestao", id)
                .when()
                        .delete(DELETAR_QUESTAO)
                ;
    }
}
