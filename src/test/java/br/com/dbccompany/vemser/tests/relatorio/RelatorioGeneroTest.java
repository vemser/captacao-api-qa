package br.com.dbccompany.vemser.tests.relatorio;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.relatorio.RelatorioGeneroModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.RelatorioClient;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint para emissão de relatório de gênero")
public class RelatorioGeneroTest extends BaseTest {

    private static RelatorioClient relatorioClient = new RelatorioClient();

    @Test
    @DisplayName("Cenário 1: Deve retonar 200 ao listar com sucesso relatório de candidatos por gênero")
    public void testListarRelatorioGeneroComSucesso() {

        var response = relatorioClient.listarCandidatosGenero()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioGeneroModel[].class);

        List<RelatorioGeneroModel> listaRelatorioGenero = Arrays.stream(response).toList();

        if (listaRelatorioGenero.size() != 0) {
            for (RelatorioGeneroModel r : listaRelatorioGenero) {
                Assertions.assertNotNull(r.getGenero());
            }
        }
    }

    @Test
    @DisplayName("Cenário 2: Deve retonar 403 ao listar relatório de candidatos por gênero sem autenticação")
    public void testListarRelatorioGeneroComSucessoSemAutenticacao() {

        var response = relatorioClient.listarCandidatosGeneroSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
