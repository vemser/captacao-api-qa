package br.com.dbccompany.vemser.tests.questao;

import client.questao.QuestaoClient;
import client.questao.QuestaoPraticaClient;
import factory.questao.QuestaoPraticaDataFactory;
import models.questao.QuestaoPraticaModel;
import models.questao.QuestaoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static factory.questao.QuestaoObjetivaDataFactory.criarCincoAlternativas;
import static factory.questao.QuestaoObjetivaDataFactory.criarCincoAlternativasFalsas;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Endpoint de editar questao pratica")
class EditarQuestaoPraticaTest {

    private final QuestaoPraticaClient questaoPraticaClient = new QuestaoPraticaClient();
    private final QuestaoClient questaoClient = new QuestaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando editar questão pratica com sucesso")
    void testEditarQuestaoPraticaComSucesso() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoPraticaValida();
        QuestaoPraticaModel questaoEditada = QuestaoPraticaDataFactory.questaoPraticaValida();

        QuestaoResponse cadastroResponse = questaoPraticaClient.criarQuestaoPratica(questao)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

        QuestaoResponse edicaoResponse = questaoPraticaClient.editarQuestaoPratica(cadastroResponse.getId(), questaoEditada)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

        assertAll(
                () -> Assertions.assertEquals("Editado com sucesso", edicaoResponse.getMensagem()),
                () -> Assertions.assertNotNull(edicaoResponse.getId())
        );

        questaoClient.deletarQuestao(edicaoResponse.getId());

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 400 quando tentar editar questão pratica com exemplos vazios")
    void testEditarQuestaoPraticaComExemplosVazios() {
        QuestaoPraticaModel questao = QuestaoPraticaDataFactory.questaoPraticaValida();

        QuestaoResponse cadastroResponse = questaoPraticaClient.criarQuestaoPratica(questao)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(QuestaoResponse.class)
                ;

        questao.setExemplos(QuestaoPraticaDataFactory.criarExemplosVazios());

        QuestaoResponse edicaoResponse = questaoPraticaClient.editarQuestaoPratica(cadastroResponse.getId(), questao)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;
    }

}
