package br.com.dbccompany.vemser.tests.prova;

import client.prova.ProvaClient;
import models.prova.ProvaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de listar prova")
public class ListarProvaTest {
    private static final ProvaClient provaClient = new ProvaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando listar todas as prova")
    void


    testListarTodasAsProvas() {

        ProvaResponse listaDeQuestoes = provaClient.listarProva()
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando listar prova por id")
    void testListarProvaPorId() {

        ProvaResponse listaDeQuestoes = provaClient.pegarProva(113)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(ProvaResponse.class)
                ;
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 200 quando listar provas por data")
    void testListarQuestoesPorData() {

        ProvaResponse listaDeQuestoes = provaClient.listarProvasPorDatas(0, 10, "03-03-2024", "03-04-2024")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 4: Deve retornar 200 quando listar provas por palavra chave")
    void testListarQuestoesPorPalavraChave() {

        ProvaResponse listaDeQuestoes = provaClient.listarProvasPorPalavraChave("VEM", 0, 10)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 5: Deve retornar 200 quando listar provas por edicao")
    void testListarQuestoesPorEdicao() {

        ProvaResponse listaDeQuestoes = provaClient.listarProvasPorEdicao(0, 10, 1221)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }

    @Test
    @DisplayName("Cenário 6: Deve retornar 200 quando listar provas versao")
    void testListarQuestoesPorVersao() {

        ProvaResponse listaDeQuestoes = provaClient.listarProvasPorVersao(0, 10, 1114388544)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(ProvaResponse.class);
    }
}
