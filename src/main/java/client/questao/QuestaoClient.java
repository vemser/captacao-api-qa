package client.questao;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.questoes.QuestaoObjetivaModel;
import models.questoes.QuestaoPraticaModel;
import specs.questoes.QuestoesSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class QuestaoClient {
    private static final String AUTHORIZATION = "Authorization";
    private static final String CRIAR_PRATICA = "/questao/criar-pratica";
    private static final String CRIAR_OBJETIVA = "/questao/criar-objetiva";
    private static final String LISTAR_QUESTOES = "/questao";
    private static final String POR_TIPO = "/questao/por-tipo";
    private static final String POR_DIFICULDADE = "/questao/por-dificuldade";
    private static final String LISTAR_QUESTOES_ALEATORIAS = "/questao/listar-questoes-aleatorias";

    public Response criarQuestaoObjetiva(QuestaoObjetivaModel questaoObjetivaModel){
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestoesSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(questaoObjetivaModel)
                    .when()
                        .post(CRIAR_OBJETIVA)
                ;
    }

    public Response criarQuestaoPratica(QuestaoPraticaModel questaoPraticaModel){
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestoesSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(questaoPraticaModel)
                        .when()
                        .post(CRIAR_PRATICA)
                ;
    }

    public String getListarQuestoes() {
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestoesSpecs.questoesReqSpec())
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
                        .spec(QuestoesSpecs.questoesReqSpec())
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
                        .spec(QuestoesSpecs.questoesReqSpec())
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
                        .spec(QuestoesSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .when()
                        .get(LISTAR_QUESTOES_ALEATORIAS)
                        .thenReturn()
                        .asString()
                ;
    }
}
