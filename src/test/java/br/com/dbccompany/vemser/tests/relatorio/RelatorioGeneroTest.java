package br.com.dbccompany.vemser.tests.relatorio;

import client.EdicaoClient;
import client.RelatorioClient;
import models.relatorio.RelatorioGeneroModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de gênero")
class RelatorioGeneroTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_GENERO = "schemas/relatorio/listar_relatorios_genero.json";
    private static final String ERRO_GENERO_NULO = "Campo 'genero' deve ser não nulo";
    private static String edicao;

    @BeforeEach
    public void setUp() {
        edicao = edicaoClient.getEdicaoAtualComoString();
    }

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por genero")
    @Tag("Contract")
    public void testValidarContratoListarRelatoriosPorGenero() {

        relatorioClient.listarCandidatosGenero(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_GENERO))
        ;

    }

    @Test
    @DisplayName("Cenário 2: Deve retonar 200 ao listar com sucesso relatório de candidatos por gênero")
    @Tag("Regression")
    void testListarRelatorioGeneroComSucesso() {

        RelatorioGeneroModel[] response = relatorioClient.listarCandidatosGenero(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioGeneroModel[].class)
                ;

        List<RelatorioGeneroModel> listaRelatorioGenero = Arrays.stream(response).toList();

        Assertions.assertAll(
                listaRelatorioGenero.stream()
                        .map(r -> (Executable) () ->
                                Assertions.assertNotNull(r.getGenero(), ERRO_GENERO_NULO)
                        )
                        .toList()
        );

    }

    @Test
    @DisplayName("Cenário 3: Deve retonar 403 ao listar relatório de candidatos por gênero sem autenticação")
    @Tag("Regression")
    void testListarRelatorioGeneroComSucessoSemAutenticacao() {

        relatorioClient.listarCandidatosGeneroSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }
}
