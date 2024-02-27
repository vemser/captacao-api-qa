package br.com.dbccompany.vemser.tests.trilha;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.TrilhaDataFactory;
import models.trilha.TrilhaApenasNomeModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.TrilhaClient;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de listagem de trilhas")
class ListarTrilhaTest extends BaseTest {

    private static TrilhaClient trilhaClient = new TrilhaClient();
    private static TrilhaDataFactory trilhaDataFactory = new TrilhaDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista as trilhas com sucesso")
    void testListarTrilhasComSucesso() {
        String nomeTrilha = "TRILHA_TESTE";

        TrilhaApenasNomeModel trilha = trilhaDataFactory.trilhaValidaApenasNomePassandoNome(nomeTrilha);

        TrilhaModel trilhaCadastrada = trilhaClient.criarTrilhaPassandoNome(trilha)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel.class);

        var response = trilhaClient.listarTodasAsTrilhas()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel[].class);

        List<TrilhaModel> listaDeTrilhas = Arrays.stream(response).toList();

        Boolean trilhaCriadaFoiListada = false;

        for (TrilhaModel t : listaDeTrilhas) {
            if (t.getNome().toLowerCase().equals(nomeTrilha.toLowerCase())) {
                trilhaCriadaFoiListada = true;
            }
        }

        var deletarTrilha = trilhaClient.deletarTrilha(trilhaCadastrada.getIdTrilha())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertNotNull(listaDeTrilhas);
        Assertions.assertTrue(trilhaCriadaFoiListada);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando lista as trilhas sem autenticação")
    void testListarTrilhasSemAutenticacao() {

        var response = trilhaClient.listarTodasAsTrilhasSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }
}
