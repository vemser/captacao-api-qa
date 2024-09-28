package br.com.dbccompany.vemser.tests.relatorio;

import client.EdicaoClient;
import client.RelatorioClient;
import models.relatorio.RelatorioEstadoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de estados")
class RelatorioEstadoTest  {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_ESTADO = "schemas/relatorio/listar_relatorios_estado.json";
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final String ERRO_ESTADO_NULO = "Campo 'estado' deve ser não nulo";
    private static String edicao;

    @BeforeEach
    public void setUp() {
        edicao = edicaoClient.getEdicaoAtualComoString();
    }

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por estado")
    @Tag("Contract")
    public void testValidarContratoListarRelatoriosPorEstado() {

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

        RelatorioEstadoModel[] response = relatorioClient.listarCandidatosEstado(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEstadoModel[].class)
                ;

        List<RelatorioEstadoModel> listaRelatorioEstado = Arrays.stream(response).toList();

        Assertions.assertAll(
                listaRelatorioEstado.stream()
                    .map(r -> (Executable) () ->
                            Assertions.assertNotNull(r.getEstado(), ERRO_ESTADO_NULO)
                    )
                    .toList()
        );

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
