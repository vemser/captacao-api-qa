package br.com.dbccompany.vemser.tests.questao;

import client.questao.QuestaoClient;
import factory.questao.QuestaoPraticaDataFactory;
import models.questao.QuestaoPraticaModel;
import models.questao.QuestaoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de cadastrar prova prática")
class CadastrarQuestaoPraticaTest {

    private final QuestaoClient questaoClient = new QuestaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 quando cadastra questão pratica com sucesso")
    void testCadastrarQuestaoPraticaComSucesso() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoPraticaValida();

        QuestaoResponse questaoResponse = questaoClient.criarQuestaoPratica(questao)
                .then()
                .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

        questaoResponse.getMensagem();
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 400 ao tentar criar questão sem título")
    void testCadastrarQuestaoPraticaSemTitulo() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoSemTitulo();

        QuestaoResponse questaoPraticaModel = questaoClient.criarQuestaoPratica(questao)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(QuestaoResponse.class);
    }
    @Test
    @DisplayName("Cenário 3: Deve retornar 400 ao tentar criar questão prática com título excedendo os 100 caracteres")
    void testCadastrarQuestaoPraticaComTituloExcedendoOs100Caracteres() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoExcedendoOs100CaracteresNoTitulo();

        QuestaoResponse questaoPraticaModel = questaoClient.criarQuestaoPratica(questao)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(QuestaoResponse.class);
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 ao tentar criar questão prática com enunciado vazio")
    void testCadastrarQuestaoPraticaComEnunciadoVazio() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoComPraticaComEnunciadoVazio();

        QuestaoResponse questaoPraticaModel = questaoClient.criarQuestaoPratica(questao)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(QuestaoResponse.class);
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 400 ao tentar criar questão prática excedendo os 4000 caracteres no enunciado")
    void testCadastrarQuestaoPraticaExcedendoOs4000CaracteresNoEnunciado() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoComPraticaExcedendoOs4000CaracteresNoEnunciado();

        QuestaoResponse questaoPraticaModel = questaoClient.criarQuestaoPratica(questao)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(QuestaoResponse.class);
    }

    @Test
    @DisplayName("Cenário 6: Deve retornar 400 ao tentar criar questão prática excedendo os 4000 caracteres no enunciado")
    void testCadastrarQuestaoPraticaComDificuldadeInexistente() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoComPraticaComDificuldadeInexistente();

        QuestaoResponse questaoPraticaModel = questaoClient.criarQuestaoPratica(questao)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(QuestaoResponse.class);
    }

    @Test
    @DisplayName("Cenário 7: Deve retornar 400 ao tentar criar questão prática com exemplos vazios")
    void testCadastrarQuestaoPraticaComExemplosVazios() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoComPraticaComExemplosVazios();

        QuestaoResponse questaoPraticaModel = questaoClient.criarQuestaoPratica(questao)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(QuestaoResponse.class);
    }
}
