package br.com.dbccompany.vemser.tests.questao;

import client.questao.QuestaoClient;
import factory.questao.QuestaoDataFactory;
import models.prova.ProvaCriacaoResponseModel;
import models.questao.QuestaoObjetivaModel;
import models.questao.QuestaoObjetivaResponse;
import models.questao.QuestaoPraticaModel;
import models.questao.QuestaoPraticaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

public class CadastrarQuestãoTest {

    QuestaoClient questaoClient = new QuestaoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 quando cadastra questão pratica com sucesso")
    public void testCadastrarQuestaoPraticaComSucesso() {
        QuestaoPraticaModel questao = QuestaoDataFactory.questaoPraticaValida();

        QuestaoPraticaResponse questaoPraticaResponse = questaoClient.criarQuestaoPratica(questao)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(QuestaoPraticaResponse.class)
                ;

        assertAll(
                () -> Assertions.assertEquals("CADASTRO_COM_SUCESSO", questaoPraticaResponse.getMensagem()),
                () -> Assertions.assertNotNull(questaoPraticaResponse.getIdCandidatoProva())

        );
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 201 quando cadastra questão objetiva com sucesso")
    public void testCadastrarQuestaoObjetivaComSucesso() {
        QuestaoObjetivaModel questao = QuestaoDataFactory.questaoObjetivaValida();

        QuestaoObjetivaResponse questaoObjetivaResponse = questaoClient.criarQuestaoObjetiva(questao)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(QuestaoObjetivaResponse.class)
                ;

        assertAll(
                () -> Assertions.assertEquals("CADASTRO_COM_SUCESSO", questaoObjetivaResponse.getMensagem()),
                () -> Assertions.assertNotNull(questaoObjetivaResponse.getIdCandidatoProva())

        );
    }

}
