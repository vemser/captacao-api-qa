package br.com.dbccompany.vemser.tests.trilha;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.TrilhaDataFactory;
import models.trilha.TrilhaApenasNomeModel;
import models.trilha.TrilhaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.TrilhaClient;

@DisplayName("Endpoint de remoção de trilhas")
public class DeletarTrilhaTest extends BaseTest {

    private static TrilhaClient trilhaClient = new TrilhaClient();
    private static TrilhaDataFactory trilhaDataFactory = new TrilhaDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 quando deleta trilha com sucesso")
    public void testDeletarTrilhaComSucesso() {
        String nomeTrilha = "TRILHA_TESTE";

        TrilhaApenasNomeModel trilha = trilhaDataFactory.trilhaValidaApenasNomePassandoNome(nomeTrilha);

        TrilhaModel trilhaCadastrada = trilhaClient.criarTrilhaPassandoNome(trilha)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel.class);

        var deletarTrilha = trilhaClient.deletarTrilha(trilhaCadastrada.getIdTrilha())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        var confirmaDelecaoTrilha = trilhaClient.deletarTrilha(trilhaCadastrada.getIdTrilha())
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando deleta trilha sem autenticação")
    public void testDeletarTrilhaSemAutenticacao() {
        String nomeTrilha = "TRILHA_TESTE";

        TrilhaApenasNomeModel trilha = trilhaDataFactory.trilhaValidaApenasNomePassandoNome(nomeTrilha);

        TrilhaModel trilhaCadastrada = trilhaClient.criarTrilhaPassandoNome(trilha)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(TrilhaModel.class);

        var deletarTrilha = trilhaClient.deletarTrilha(trilhaCadastrada.getIdTrilha())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        var confirmaDelecaoTrilha = trilhaClient.deletarTrilha(trilhaCadastrada.getIdTrilha())
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
