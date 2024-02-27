package br.com.dbccompany.vemser.tests.relatorio;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.relatorio.RelatorioNeurodiversidadeModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.RelatorioClient;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint para emissão de relatório de neurodiversidade")
public class RelatorioNeurodiversidadeTest extends BaseTest {

    private static RelatorioClient relatorioClient = new RelatorioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao retornar relatório com quantidade de candidatos com neurodiversidade com sucesso")
    public void testListarRelatorioNeurodiversidadeComSucesso() {

        var response = relatorioClient.listarCandidatosNeurodiversidade()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioNeurodiversidadeModel[].class);

        List<RelatorioNeurodiversidadeModel> listaRelatorioNeurodiversidade = Arrays.stream(response).toList();

        if (listaRelatorioNeurodiversidade.size() != 0) {
            for (RelatorioNeurodiversidadeModel r : listaRelatorioNeurodiversidade) {
                Assertions.assertNotNull(r.getNeurodiversidade());
            }
        }
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao retornar relatório com quantidade de candidatos com neurodiversidade sem autenticação")
    public void testListarRelatorioNeurodiversidadeSemAutenticacao() {

        var response = relatorioClient.listarCandidatosNeurodiversidadeSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
