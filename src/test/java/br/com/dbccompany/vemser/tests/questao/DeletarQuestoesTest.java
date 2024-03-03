package br.com.dbccompany.vemser.tests.questao;

import client.questao.QuestaoClient;
import client.questao.QuestaoObjetivaClient;
import factory.questao.QuestaoObjetivaDataFactory;
import io.restassured.response.Response;
import models.questao.QuestaoObjetivaModel;
import models.questao.QuestaoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de deletar questões")
class DeletarQuestoesTest {

    private final QuestaoClient questaoClient = new QuestaoClient();

    private final QuestaoObjetivaClient questaoObjetivaClient = new QuestaoObjetivaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando deletar uma questão")
    void testDeletarQuestao() {

        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaValida();

        QuestaoResponse questaoObjetivaResponse = questaoObjetivaClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(QuestaoResponse.class)
                ;

        Response response = questaoClient.deletarQuestao(questaoObjetivaResponse.getId());
        response.then().log().all().statusCode(HttpStatus.SC_OK);


    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando tentar deletar uma questão sem autenticação")
    void testDeletarQuestaoSemAutenticacao() {

        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaValida();

        QuestaoResponse questaoObjetivaResponse = questaoObjetivaClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(QuestaoResponse.class)
                ;

        Response response = questaoClient.deletarQuestao(questaoObjetivaResponse.getId());
        response.then().log().all().statusCode(HttpStatus.SC_FORBIDDEN);

    }

}
