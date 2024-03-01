package br.com.dbccompany.vemser.tests.relatorio;

import client.relatorio.RelatorioClient;
import models.relatorio.RelatorioPcdModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint para emissão de relatório de PCD")
class RelatorioPcdTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar relatório de candidatos pcd com sucesso")
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

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao listar relatório de candidatos pcd sem autenticação")
    void testListarRelatorioPcfSemAutenticacao() {

        var response = relatorioClient.listarCandidatosPcdSemAutenticacao()
                    .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
