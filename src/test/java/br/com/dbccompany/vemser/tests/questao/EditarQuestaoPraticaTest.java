package br.com.dbccompany.vemser.tests.questao;

import client.questao.QuestaoPraticaClient;
import factory.questao.QuestaoPraticaDataFactory;
import models.questao.QuestaoPraticaModel;
import models.questao.QuestaoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de cadastrar prova objetiva")
class EditarQuestaoPraticaTest {

    private final QuestaoPraticaClient questaoPraticaClient = new QuestaoPraticaClient();

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

        QuestaoResponse edicaoResponse = questaoPraticaClient.editarQuestaoPratica(cadastroResponse.getMensagem(), questaoEditada)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;
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

        QuestaoResponse edicaoResponse = questaoPraticaClient.editarQuestaoPratica(cadastroResponse.getMensagem(), questao)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(QuestaoResponse.class)
                ;
    }

}
