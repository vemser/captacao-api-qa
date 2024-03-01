package br.com.dbccompany.vemser.tests.relatorio;

import client.relatorio.RelatorioClient;
import models.relatorio.RelatorioEstadoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint para emissão de relatório de estados")
class RelatorioEstadoTest  {

    private static final RelatorioClient relatorioClient = new RelatorioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar com sucesso relatório de candidatos por estado")
    void testListarRelatorioEstadoComSucesso() {

        var response = relatorioClient.listarCandidatosEstado()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEstadoModel[].class);

        List<RelatorioEstadoModel> listaRelatorioEstado = Arrays.stream(response).toList();

        if (!listaRelatorioEstado.isEmpty()) {
            for (RelatorioEstadoModel r : listaRelatorioEstado) {
                Assertions.assertNotNull(r.getEstado());
            }
        }
    }

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao listar relatório de candidatos por estado sem autenticação")
    void testListarRelatorioEstadoComSucessoSemAutenticacao() {

        var response = relatorioClient.listarCandidatosEstadoSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
