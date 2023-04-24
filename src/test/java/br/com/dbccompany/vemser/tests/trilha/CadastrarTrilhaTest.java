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

public class CadastrarTrilhaTest extends BaseTest {

    private static TrilhaDataFactory trilhaDataFactory = new TrilhaDataFactory();
    private static TrilhaService trilhaService = new TrilhaService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 quando cadastra trilha com sucesso")
    public void testCadastrarTrilhaComSucesso() {
        String nomeTrilha = "TRILHA_TESTE";

        TrilhaApenasNomeModel trilha = trilhaDataFactory.trilhaValidaApenasNomePassandoNome(nomeTrilha);

        TrilhaModel trilhaCadastrada = trilhaService.criarTrilhaPassandoNome(trilha)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel.class);

        var deletarTrilha = trilhaService.deletarTrilha(trilhaCadastrada.getIdTrilha())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);


        Assertions.assertNotNull(trilhaCadastrada);
        Assertions.assertEquals(trilha.getNome(), trilhaCadastrada.getNome());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando cadastra trilha sem autenticação")
    public void testCadastrarTrilhaSemAutenticacao() {
        String nomeTrilha = "TRILHA_TESTE";

        TrilhaApenasNomeModel trilha = trilhaDataFactory.trilhaValidaApenasNomePassandoNome(nomeTrilha);

        var trilhaCadastrada = trilhaService.criarTrilhaPassandoNomeSemAutenticacao(trilha)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
