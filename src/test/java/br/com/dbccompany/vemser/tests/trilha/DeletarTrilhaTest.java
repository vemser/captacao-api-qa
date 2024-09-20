package br.com.dbccompany.vemser.tests.trilha;

import client.trilha.TrilhaClient;
import factory.trilha.TrilhaDataFactory;
import models.trilha.TrilhaModel;
import models.trilha.TrilhaResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de deletar trilhas")
class DeletarTrilhaTest {

    TrilhaClient trilhaClient = new TrilhaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 quando deleta trilha com sucesso")
    @Tag("Regression")
    void testDeletarTrilhaComSucesso() {
        TrilhaModel trilha = TrilhaDataFactory.trilhaValida();

        TrilhaResponse trilhaResponse = trilhaClient.cadastrarTrilha(trilha)
                .then()
                .statusCode(200)
                .extract().as(TrilhaResponse.class);

        trilhaClient.deletarTrilhaPorId(trilhaResponse.getIdTrilha());
    }

}
