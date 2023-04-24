package br.com.dbccompany.vemser.tests.trilha;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.TrilhaDataFactory;
import models.trilha.TrilhaApenasNomeModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.TrilhaService;

import java.util.Arrays;
import java.util.List;

public class ListarTrilhaTest extends BaseTest {

    private static TrilhaService trilhaService = new TrilhaService();
    private static TrilhaDataFactory trilhaDataFactory = new TrilhaDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista as trilhas com sucesso")
    public void testListarTrilhasComSucesso() {
        String nomeTrilha = "TRILHA_TESTE";

        TrilhaApenasNomeModel trilha = trilhaDataFactory.trilhaValidaApenasNomePassandoNome(nomeTrilha);

        TrilhaModel trilhaCadastrada = trilhaService.criarTrilhaPassandoNome(trilha)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel.class);

        var response = trilhaService.listarTodasAsTrilhas()
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

        var deletarTrilha = trilhaService.deletarTrilha(trilhaCadastrada.getIdTrilha())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertNotNull(listaDeTrilhas);
        Assertions.assertTrue(trilhaCriadaFoiListada);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando lista as trilhas sem autenticação")
    public void testListarTrilhasSemAutenticacao() {

        var response = trilhaService.listarTodasAsTrilhasSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }
}
