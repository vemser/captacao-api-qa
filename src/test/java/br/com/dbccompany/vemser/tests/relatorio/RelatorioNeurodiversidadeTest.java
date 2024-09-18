package br.com.dbccompany.vemser.tests.relatorio;

import client.relatorio.RelatorioClient;
import models.relatorio.RelatorioNeurodiversidadeModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de neurodiversidade")
class RelatorioNeurodiversidadeTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_NEURODIVERSIDADE = "schemas/relatorio/listar_relatorios_neurodiversidade.json";

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por neurodiversidade")
    public void testValidarContratoListarRelatoriosPorNeurodiversidade() {
        relatorioClient.listarCandidatosNeurodiversidade()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_NEURODIVERSIDADE))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao retornar relatório com quantidade de candidatos com neurodiversidade com sucesso")
    void testListarRelatorioNeurodiversidadeComSucesso() {

        var response = relatorioClient.listarCandidatosNeurodiversidade()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioNeurodiversidadeModel[].class);

        List<RelatorioNeurodiversidadeModel> listaRelatorioNeurodiversidade = Arrays.stream(response).toList();

        if (!listaRelatorioNeurodiversidade.isEmpty()) {
            for (RelatorioNeurodiversidadeModel r : listaRelatorioNeurodiversidade) {
                Assertions.assertNotNull(r.getNeurodiversidade());
            }
        }
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao retornar relatório com quantidade de candidatos com neurodiversidade sem autenticação")
    void testListarRelatorioNeurodiversidadeSemAutenticacao() {

        relatorioClient.listarCandidatosNeurodiversidadeSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
