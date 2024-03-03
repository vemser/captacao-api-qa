package br.com.dbccompany.vemser.tests.questao;

import client.questao.QuestaoClient;
import factory.questao.QuestaoObjetivaDataFactory;
import models.questao.QuestaoObjetivaModel;
import models.questao.QuestaoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de cadastrar prova objetiva")
class CadastrarQuestaoObjetivaTest {

    private final QuestaoClient questaoClient = new QuestaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 quando cadastra questão objetiva com sucesso")
    void testCadastrarQuestaoObjetivaComSucesso() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaValida();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 400 quando tenta cadastrar questão objetiva com título vazio/em branco")
    void testCadastrarQuestaoObjetivaComTituloEmBranco() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoSemTitulo();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 400 quando tenta cadastrar questão objetiva excedendo 100 caracteres no título")
    void testCadastrarQuestaoObjetivaExcedendoOs100Caracteres() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoExcedendoOs100CaracteresNoTitulo();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 quando tenta cadastrar questão objetiva com enunciado vazio")
    void testCadastrarQuestaoObjetivaComEnunciadoVazio() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaComEnunciadoVazio();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 400 quando tenta cadastrar questão objetiva excedendo os 4000 caracteres no enunciado")
    void testCadastrarQuestaoObjetivaExcedendoOs4000CaracteresNoEnunciado() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaExcedendoOs4000CaracteresNoEnunciado();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 6: Deve retornar 400 quando tenta cadastrar questão objetiva com dificuldade inexistente")
    void testCadastrarQuestaoObjetivaComDificuldadeInexistente() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaComDificuldadeInexistente();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 7: Deve retornar 400 quando tenta cadastrar questão objetiva com nenhuma alternativa correta")
    void testCadastrarQuestaoObjetivaComNenhumaAlternativaCorreta() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaComNenhumaAlternativaCorreta();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 8: Deve retornar 400 quando tenta cadastrar questão objetiva com apenas uma alternativa")
    void testCadastrarQuestaoObjetivaComApenasUmaAlternativa() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaComApenasUmaAlternativa();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }


    @Test
    @DisplayName("Cenário 9: Deve retornar 400 quando tenta cadastrar questão objetiva com mais de cinco alternativas")
    void testCadastrarQuestaoObjetivaComMaisDeCincoAlternativas() {
        QuestaoObjetivaModel questao = QuestaoObjetivaDataFactory.questaoObjetivaComMaisDeCincoAlternativas();

        QuestaoResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(QuestaoResponse.class)
                ;
    }
}
