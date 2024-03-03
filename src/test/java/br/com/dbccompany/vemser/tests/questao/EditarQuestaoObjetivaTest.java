package br.com.dbccompany.vemser.tests.questao;

import client.questao.QuestaoObjetivaClient;
import factory.questao.QuestaoObjetivaDataFactory;
import models.questao.QuestaoObjetivaModel;
import models.questao.QuestaoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static factory.questao.QuestaoObjetivaDataFactory.criarCincoAlternativas;
import static factory.questao.QuestaoObjetivaDataFactory.criarCincoAlternativasFalsas;

@DisplayName("Endpoint de editar questão objetiva")
class EditarQuestaoObjetivaTest {

    private final QuestaoObjetivaClient questaoObjetivaClient = new QuestaoObjetivaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando editar questão objetiva com sucesso")
    void testEditarQuestaoObjetivaComSucesso() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaValida();
        QuestaoObjetivaModel questaoEditada = QuestaoObjetivaDataFactory.questaoObjetivaValida();

        QuestaoResponse cadastroResponse = questaoObjetivaClient.criarQuestaoObjetiva(questao)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(QuestaoResponse.class)
                ;



        QuestaoResponse edicaoResponse = questaoObjetivaClient.editarQuestaoObjetiva(cadastroResponse.getId(), questaoEditada)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando editar questão objetiva adicionando alternativas")
    void testEditarQuestaoObjetivaAdicionandoAlternativas() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaComDuasAlternativas();

        QuestaoResponse cadastroResponse = questaoObjetivaClient.criarQuestaoObjetiva(questao)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

        questao.setAlternativasObjetivas(criarCincoAlternativas());

        QuestaoResponse edicaoResponse = questaoObjetivaClient.editarQuestaoObjetiva(cadastroResponse.getId(), questao)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 400 quando tentar editar questão objetiva sem alternativa correta")
    void testEditarQuestaoObjetivaSemAlternativaCorreta() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaComDuasAlternativas();

        QuestaoResponse cadastroResponse = questaoObjetivaClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(QuestaoResponse.class);

        questao.setAlternativasObjetivas(criarCincoAlternativasFalsas());

        QuestaoResponse edicaoResponse = questaoObjetivaClient.editarQuestaoObjetiva(cadastroResponse.getId(), questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class);
    }

}
