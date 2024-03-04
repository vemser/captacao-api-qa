package br.com.dbccompany.vemser.tests.prova;

import client.prova.ProvaClient;
import factory.prova.ProvaDataFactory;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Endpoint de cadastrar prova")
class CadastrarProvaTest {
    private static final ProvaClient provaClient = new ProvaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando cadastra prova com sucesso")
    void testCadastrarProvaComSucesso() {

        ProvaCriacaoModel prova = ProvaDataFactory.provaValida();

        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(ProvaResponse.class);

        assertAll(
                () -> Assertions.assertEquals("Cadastro realizado com sucesso", provaCriada.getMensagem()),
                () -> Assertions.assertNotNull(provaCriada.getId())
        );

        provaClient.deletarProva(provaCriada.getId());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando cadastra prova com questões aleatórias")
    void testCadastrarProvaComQuestoesAleatorias() {

        ProvaCriacaoModel prova = ProvaDataFactory.provaComQuestoesAleatorias();

        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        assertAll(
                () -> Assertions.assertEquals("Cadastro realizado com sucesso", provaCriada.getMensagem()),
                () -> Assertions.assertNotNull(provaCriada.getId())
        );

        provaClient.deletarProva(provaCriada.getId());
    }
    @Test
    @DisplayName("Cenário 3: Deve retornar 400 quando tenta cadastrar prova com Título com mais de 100 caracteres")
    void testCadastrarProvaComTituloExcedendo100Caracteres() {

        ProvaCriacaoModel prova = ProvaDataFactory.provaComTituloExcedendoOs100Caracteres();

        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 400 quando tenta cadastrar prova com titulo vazio")
    void testCadastrarProvaComTituloVazio() {

        ProvaCriacaoModel prova = ProvaDataFactory.provaComTituloVazio();

        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 400 quando tenta cadastrar prova sem selecionar questões")
    void testCadastrarProvaSemSelecionarQuestoes() {

        ProvaCriacaoModel prova = ProvaDataFactory.criarProvaSemSelecionarQuestoes();

        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 6: Deve retornar 400 quando tenta cadastrar prova com mais de 10 questões")
    void testCadastrarProvaComMaisDe10Questoes() {

        ProvaCriacaoModel prova = ProvaDataFactory.criarProvaComMaisDe10Questoes(15);

        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 7: Deve retornar 403 quando cadastra sem autenticação")
    void testCadastrarProvaSemAutenticacao() {

        ProvaCriacaoModel prova = ProvaDataFactory.provaValida();

        ProvaResponse provaCriada = provaClient.criarProvaSemAutenticacao(prova)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .as(ProvaResponse.class);
    }
}
