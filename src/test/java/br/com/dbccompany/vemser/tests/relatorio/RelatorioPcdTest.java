package br.com.dbccompany.vemser.tests.relatorio;

import client.EdicaoClient;
import client.RelatorioClient;
import models.relatorio.RelatorioNeurodiversidadeModel;
import models.relatorio.RelatorioPcdModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de PCD")
class RelatorioPcdTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_PCD = "schemas/relatorio/listar_relatorios_pcd.json";
    private static final String ERRO_PCD_NULO = "Campo 'pcd' deve ser não nulo";
    private static String edicao;

    @BeforeEach
    public void setUp() {
        edicao = edicaoClient.getEdicaoAtualComoString();
    }

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por pcd")
    @Tag("Contract")
    public void testValidarContratoListarRelatoriosPorPcd() {

        relatorioClient.listarCandidatosPcd(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_PCD))
        ;

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao listar relatório de candidatos pcd com sucesso")
    @Tag("Regression")
    void testListarRelatorioPcdComSucesso() {

        RelatorioPcdModel[] response = relatorioClient.listarCandidatosPcd(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioPcdModel[].class)
                ;

        List<RelatorioPcdModel> listaRelatorioPcd = Arrays.stream(response).toList();

        Assertions.assertAll(
                listaRelatorioPcd.stream()
                        .map(r -> (Executable) () ->
                                Assertions.assertNotNull(r.getPcd(), ERRO_PCD_NULO)
                        )
                        .toList()
        );

    }
}
