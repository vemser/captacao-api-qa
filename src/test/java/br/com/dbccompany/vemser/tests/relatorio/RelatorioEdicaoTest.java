package br.com.dbccompany.vemser.tests.relatorio;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.relatorio.RelatorioEdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.RelatorioService;

import java.util.Arrays;
import java.util.List;

public class RelatorioEdicaoTest extends BaseTest {

    private static RelatorioService relatorioService = new RelatorioService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar com sucesso relatório de candidato por edição")
    public void testListarRelatorioEdicaoComSucesso() {

        var response = relatorioService.listarCandidatosEdicao()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEdicaoModel[].class);

        List<RelatorioEdicaoModel> listaRelatorioEdicao = Arrays.stream(response).toList();

        if (listaRelatorioEdicao.size() != 0) {
            for (RelatorioEdicaoModel r : listaRelatorioEdicao) {
                Assertions.assertNotNull(r.getEdicao());
            }
        }
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao listar relatório de candidato por edição sem autenticação")
    public void testListarRelatorioEdicaoComSucessoSemAutenticacao() {

        var response = relatorioService.listarCandidatosEdicaoSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
