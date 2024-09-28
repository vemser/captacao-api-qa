package br.com.dbccompany.vemser.tests.relatorio;

import client.EdicaoClient;
import client.RelatorioClient;
import models.relatorio.RelatorioGeneroModel;
import models.relatorio.RelatorioNeurodiversidadeModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de neurodiversidade")
class RelatorioNeurodiversidadeTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_NEURODIVERSIDADE = "schemas/relatorio/listar_relatorios_neurodiversidade.json";
    private static final String ERRO_NEURODIVERSIDADE_NULO = "Campo 'neurodiversidade' deve ser não nulo";
    private static String edicao;

    @BeforeEach
    public void setUp() {
        edicao = edicaoClient.getEdicaoAtualComoString();
    }

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por neurodiversidade")
    @Tag("Contract")
    public void testValidarContratoListarRelatoriosPorNeurodiversidade() {

        relatorioClient.listarCandidatosNeurodiversidade(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_NEURODIVERSIDADE))
        ;

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao retornar relatório com quantidade de candidatos com neurodiversidade com sucesso")
    @Tag("Regression")
    void testListarRelatorioNeurodiversidadeComSucesso() {

        RelatorioNeurodiversidadeModel[] response = relatorioClient.listarCandidatosNeurodiversidade(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioNeurodiversidadeModel[].class)
                ;

        List<RelatorioNeurodiversidadeModel> listaRelatorioNeurodiversidade = Arrays.stream(response).toList();

        Assertions.assertAll(
                listaRelatorioNeurodiversidade.stream()
                        .map(r -> (Executable) () ->
                                Assertions.assertNotNull(r.getNeurodiversidade(), ERRO_NEURODIVERSIDADE_NULO)
                        )
                        .toList()
        );

    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 ao retornar relatório com quantidade de candidatos com neurodiversidade sem autenticação")
    @Tag("Regression")
    void testListarRelatorioNeurodiversidadeSemAutenticacao() {

        relatorioClient.listarCandidatosNeurodiversidadeSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }
}
