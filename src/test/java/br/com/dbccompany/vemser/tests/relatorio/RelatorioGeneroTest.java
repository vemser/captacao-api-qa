package br.com.dbccompany.vemser.tests.relatorio;

import client.relatorio.RelatorioClient;
import models.relatorio.RelatorioGeneroModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint para emissão de relatório de gênero")
class RelatorioGeneroTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();

    @Test
    @DisplayName("Cenário 1: Deve retonar 200 ao listar com sucesso relatório de candidatos por gênero")
    @Tag("Regression")
    void testListarRelatorioGeneroComSucesso() {

        var response = relatorioClient.listarCandidatosGenero()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioGeneroModel[].class);

        List<RelatorioGeneroModel> listaRelatorioGenero = Arrays.stream(response).toList();

        if (!listaRelatorioGenero.isEmpty()) {
            for (RelatorioGeneroModel r : listaRelatorioGenero) {
                Assertions.assertNotNull(r.getGenero());
            }
        }
    }

    @Test
    @DisplayName("Cenário 2: Deve retonar 403 ao listar relatório de candidatos por gênero sem autenticação")
    @Tag("Regression")
    void testListarRelatorioGeneroComSucessoSemAutenticacao() {

        relatorioClient.listarCandidatosGeneroSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
