package br.com.dbccompany.vemser.tests.relatorio;

import client.relatorio.RelatorioClient;
import models.relatorio.RelatorioEdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de edições")
class RelatorioEdicaoTest  {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_EDICAO = "schemas/relatorio/listar_relatorios_edicao.json";

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por edição")
    public void testValidarContratoListarRelatoriosPorEdicao() {
        relatorioClient.listarCandidatosEdicao()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_EDICAO))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao listar com sucesso relatório de candidato por edição")
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
    @DisplayName("Cenário 3: Deve retornar 403 ao listar relatório de candidato por edição sem autenticação")
    void testListarRelatorioEdicaoComSucessoSemAutenticacao() {

        relatorioClient.listarCandidatosEdicaoSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
