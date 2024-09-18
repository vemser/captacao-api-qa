package br.com.dbccompany.vemser.tests.relatorio;

import client.relatorio.RelatorioClient;
import models.relatorio.RelatorioPcdModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de PCD")
class RelatorioPcdTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_PCD = "schemas/relatorio/listar_relatorios_pcd.json";

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por pcd")
    public void testValidarContratoListarRelatoriosPorPcd() {
        relatorioClient.listarCandidatosPcd()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_PCD))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao listar relatório de candidatos pcd com sucesso")
    void testListarRelatorioPcdComSucesso() {

        var response = relatorioClient.listarCandidatosPcd()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioPcdModel[].class);

        List<RelatorioPcdModel> relatorioCandidatosPcd = Arrays.stream(response).toList();

        if (!relatorioCandidatosPcd.isEmpty()) {
            for (RelatorioPcdModel r : relatorioCandidatosPcd) {
                Assertions.assertNotNull(r.getPcd());
            }
        }
    }
}
