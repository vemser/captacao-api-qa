package br.com.dbccompany.vemser.tests.relatorio;

import client.relatorio.RelatorioClient;
import models.relatorio.RelatorioEdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint para emissão de relatório de edições")
class RelatorioEdicaoTest  {

    private static final RelatorioClient relatorioClient = new RelatorioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar com sucesso relatório de candidato por edição")
    void testListarRelatorioEdicaoComSucesso() {

        var response = relatorioClient.listarCandidatosEdicao()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEdicaoModel[].class);

        List<RelatorioEdicaoModel> listaRelatorioEdicao = Arrays.stream(response).toList();

        if (!listaRelatorioEdicao.isEmpty()) {
            for (RelatorioEdicaoModel r : listaRelatorioEdicao) {
                Assertions.assertNotNull(r.getEdicao());
            }
        }
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao listar relatório de candidato por edição sem autenticação")
    void testListarRelatorioEdicaoComSucessoSemAutenticacao() {

        var response = relatorioClient.listarCandidatosEdicaoSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
