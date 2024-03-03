package client.questao;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.questao.QuestaoObjetivaModel;
import models.questao.QuestaoPraticaModel;
import specs.questao.QuestaoSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class QuestaoPraticaClient {
    private static final String AUTHORIZATION = "Authorization";
    private static final String CRIAR_PRATICA = "/questao/criar-pratica";
    private static final String EDITAR_PRATICA = "/questao/{id}";
    private static final String LISTAR_QUESTOES = "/questao";
    private static final String POR_TIPO = "/questao/por-tipo";
    private static final String POR_DIFICULDADE = "/questao/por-dificuldade";
    private static final String LISTAR_QUESTOES_ALEATORIAS = "/questao/listar-questoes-aleatorias";

    public Response criarQuestaoPratica(QuestaoPraticaModel questaoPraticaModel){
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(questaoPraticaModel)
                        .when()
                        .post(CRIAR_PRATICA)
                ;
    }

    public Response editarQuestaoPratica(String id, QuestaoPraticaModel questaoPraticaModel){
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("id", id)
                        .body(questaoPraticaModel)
                .when()
                        .put(EDITAR_PRATICA)
                ;
    }

    public String getListarQuestoes() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                    .when()
                        .get(LISTAR_QUESTOES)
                        .thenReturn()
                        .asString()
                ;
    }

    public String getListarQuestoesPorTipo() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(POR_TIPO)
                        .thenReturn()
                        .asString()
                ;
    }
    public String getListarQuestoesPorDificuldade() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(POR_DIFICULDADE)
                        .thenReturn()
                        .asString()
                ;
    }

    public String getListarQuestoesAleatorias() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(LISTAR_QUESTOES_ALEATORIAS)
                        .thenReturn()
                        .asString()
                ;
    }
}
