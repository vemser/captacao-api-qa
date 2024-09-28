package br.com.dbccompany.vemser.tests.relatorio;

import client.EdicaoClient;
import client.RelatorioClient;
import models.relatorio.RelatorioNeurodiversidadeModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de neurodiversidade")
class RelatorioNeurodiversidadeTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_NEURODIVERSIDADE = "schemas/relatorio/listar_relatorios_neurodiversidade.json";

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por neurodiversidade")
    @Tag("Contract")
    public void testValidarContratoListarRelatoriosPorNeurodiversidade() {

        String edicao = edicaoClient.obterEdicaoAtual()
                .then()
                .extract().asString();

        relatorioClient.listarCandidatosNeurodiversidade(edicao)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_NEURODIVERSIDADE))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao retornar relatório com quantidade de candidatos com neurodiversidade com sucesso")
    @Tag("Regression")
    void testListarRelatorioNeurodiversidadeComSucesso() {

        String edicao = edicaoClient.obterEdicaoAtual()
                .then()
                .extract().asString();

        var response = relatorioClient.listarCandidatosNeurodiversidade(edicao)
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
    @DisplayName("Cenário 3: Deve retornar 403 ao retornar relatório com quantidade de candidatos com neurodiversidade sem autenticação")
    @Tag("Regression")
    void testListarRelatorioNeurodiversidadeSemAutenticacao() {

        relatorioClient.listarCandidatosNeurodiversidadeSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
