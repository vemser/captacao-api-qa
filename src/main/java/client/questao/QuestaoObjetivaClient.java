package client.questao;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.questao.QuestaoObjetivaModel;
import specs.questao.QuestaoSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class QuestaoObjetivaClient {
    private static final String AUTHORIZATION = "Authorization";
    private static final String CRIAR_OBJETIVA = "/questao/criar-objetiva";
    private static final String EDITAR_OBJETIVA = "/questao/editar-objetiva/{idQuestao}";
    private static final String LISTAR_QUESTOES = "/questao";
    private static final String POR_TIPO = "/questao/por-tipo";
    private static final String POR_DIFICULDADE = "/questao/por-dificuldade";
    private static final String LISTAR_QUESTOES_ALEATORIAS = "/questao/listar-questoes-aleatorias";

    public Response criarQuestaoObjetiva(QuestaoObjetivaModel questaoObjetivaModel){
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .body(questaoObjetivaModel)
                    .when()
                        .post(CRIAR_OBJETIVA)
                ;
    }

    public Response editarQuestaoObjetiva(Integer id, QuestaoObjetivaModel questaoObjetivaModel){
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idQuestao", id)
                        .body(questaoObjetivaModel)
                .when()
                        .put(EDITAR_OBJETIVA)
                ;
    }
}
