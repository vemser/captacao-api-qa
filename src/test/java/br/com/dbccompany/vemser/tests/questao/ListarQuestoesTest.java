package br.com.dbccompany.vemser.tests.questao;

import client.questao.QuestaoClient;
import client.questao.QuestaoObjetivaClient;
import factory.questao.QuestaoObjetivaDataFactory;
import io.restassured.response.Response;
import models.questao.QuestaoObjetivaModel;
import models.questao.QuestaoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static factory.questao.QuestaoObjetivaDataFactory.criarCincoAlternativas;
import static factory.questao.QuestaoObjetivaDataFactory.criarCincoAlternativasFalsas;

@DisplayName("Endpoint de listar questões")
class ListarQuestoesTest {

    private final QuestaoClient questaoClient = new QuestaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando listar todas as questões")
    void testListarTodasAsQuestoes() {

        QuestaoResponse listaDeQuestoes = questaoClient.listarTodasAsQuestoes()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando listar as questões por nível de dificuldade")
    void testListarQuestoesPorDificuldade() {

        QuestaoResponse listaDeQuestoes = questaoClient.listarQuestoesPorDificuldade("FACIL")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 200 quando listar as questões por palavra chave")
    void testListarQuestoesPorPalavraChave() {

        QuestaoResponse listaDeQuestoes = questaoClient.listarQuestoesPorPalavraChave("java")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 200 quando listar as questões por tipo de questão")
    void testListarQuestoesPorTipoDeQuestao() {

        QuestaoResponse listaDeQuestoes = questaoClient.listarQuestoesPorTipo("OBJETIVA")
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 200 quando listar questões aleatórias")
    void testListarQuestoesAleatorias() {

        Response response = questaoClient.listarQuestoesAleatorias("DIFICIL", 5);
        response.then().log().all().statusCode(HttpStatus.SC_OK);

    }

}
