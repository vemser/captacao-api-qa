package br.com.dbccompany.vemser.tests.relatorio;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.relatorio.RelatorioEstadoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.RelatorioService;

import java.util.Arrays;
import java.util.List;

public class RelatorioEstadoTest extends BaseTest {

    private static RelatorioService relatorioService = new RelatorioService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar com sucesso relatório de candidatos por estado")
    public void testListarRelatorioEstadoComSucesso() {

        var response = relatorioService.listarCandidatosEstado()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEstadoModel[].class);

        List<RelatorioEstadoModel> listaRelatorioEstado = Arrays.stream(response).toList();

        if (listaRelatorioEstado.size() != 0) {
            for (RelatorioEstadoModel r : listaRelatorioEstado) {
                Assertions.assertNotNull(r.getEstado());
            }
        }
    }

    @Test
    @DisplayName("Cenário 1: Deve retornar 403 ao listar relatório de candidatos por estado sem autenticação")
    public void testListarRelatorioEstadoComSucessoSemAutenticacao() {

        var response = relatorioService.listarCandidatosEstadoSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
