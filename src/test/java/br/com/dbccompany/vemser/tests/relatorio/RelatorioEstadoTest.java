package br.com.dbccompany.vemser.tests.relatorio;

import client.EdicaoClient;
import client.RelatorioClient;
import models.relatorio.RelatorioEstadoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de estados")
class RelatorioEstadoTest  {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_ESTADO = "schemas/relatorio/listar_relatorios_estado.json";
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por estado")
    @Tag("Regression")
    public void testValidarContratoListarRelatoriosPorEstado() {
        String edicao = edicaoClient.listaEdicaoAtualAutenticacao()
                .then()
                .extract().asString();

        relatorioClient.listarCandidatosEstado(edicao)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_ESTADO))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao listar com sucesso relatório de candidatos por estado")
    @Tag("Regression")
    void testListarRelatorioEstadoComSucesso() {
        String edicao = edicaoClient.listaEdicaoAtualAutenticacao()
                .then()
                .extract().asString();

        RelatorioEstadoModel[] response = relatorioClient.listarCandidatosEstado(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEstadoModel[].class)
                ;

        List<RelatorioEstadoModel> listaRelatorioEstado = Arrays.stream(response).toList();

        if (!listaRelatorioEstado.isEmpty()) {
            for (RelatorioEstadoModel r : listaRelatorioEstado) {
                Assertions.assertNotNull(r.getEstado());
            }
        }
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 ao listar relatório de candidatos por estado sem autenticação")
    @Tag("Regression")
    void testListarRelatorioEstadoComSucessoSemAutenticacao() {

        relatorioClient.listarCandidatosEstadoSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
