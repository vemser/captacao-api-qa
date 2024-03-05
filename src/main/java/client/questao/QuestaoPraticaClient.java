package client.questao;

import client.auth.AuthClient;
import io.restassured.response.Response;
import models.questao.QuestaoPraticaModel;
import specs.questao.QuestaoSpecs;
import utils.auth.Auth;

import static io.restassured.RestAssured.given;

public class QuestaoPraticaClient {
    private static final String AUTHORIZATION = "Authorization";
    private static final String CRIAR_PRATICA = "/questao/criar-pratica";
    private static final String EDITAR_PRATICA = "/questao/editar-pratica/{idQuestao}";

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

    public Response editarQuestaoPratica(Integer id, QuestaoPraticaModel questaoPraticaModel){
        Auth.usuarioInstrutor();

        return
                given()
                        .spec(QuestaoSpecs.questoesReqSpec())
                        .header(AUTHORIZATION, AuthClient.getToken())
                        .pathParam("idQuestao", id)
                        .body(questaoPraticaModel)
                .when()
                        .put(EDITAR_PRATICA)
                ;
    }
}
