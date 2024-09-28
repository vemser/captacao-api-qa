package br.com.dbccompany.vemser.tests.relatorio;

import client.EdicaoClient;
import client.RelatorioClient;
import models.relatorio.RelatorioEdicaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint para emissão de relatório de edições")
class RelatorioEdicaoTest  {

    private static final RelatorioClient relatorioClient = new RelatorioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final String PATH_SCHEMA_LISTAR_RELATORIOS_EDICAO = "schemas/relatorio/listar_relatorios_edicao.json";
    private static final String ERRO_EDICAO_DIFERENTE = "O campo 'edicao' deve corresponder ao valor esperado";
    private static final String ERRO_QUANTIDADE_DIFERENTE = "Campo 'quantidade' deve ser maior que zero";
    private static String edicao;

    @BeforeEach
    public void setUp() {
        edicao = edicaoClient.getEdicaoAtualComoString();
    }

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar relatórios por edição")
    @Tag("Contract")
    public void testValidarContratoListarRelatoriosPorEdicao() {

        relatorioClient.listarCandidatosEdicao(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_RELATORIOS_EDICAO))
        ;

    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 ao listar com sucesso relatório de candidato por edição")
    @Tag("Regression")
    void testListarRelatorioEdicaoComSucesso() {

        RelatorioEdicaoModel[] response = relatorioClient.listarCandidatosEdicao(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEdicaoModel[].class);

        List<RelatorioEdicaoModel> listaRelatorioEdicao = Arrays.stream(response).toList();

        Assertions.assertAll(
                listaRelatorioEdicao.stream()
                        .map(r -> (Executable) () -> {
                            Assertions.assertEquals(edicao, r.getEdicao(), ERRO_EDICAO_DIFERENTE);
                            Assertions.assertTrue(r.getQuantidade() > 0, ERRO_QUANTIDADE_DIFERENTE);
                        })
                        .toList()
        );

    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 ao listar relatório de candidato por edição sem autenticação")
    @Tag("Regression")
    void testListarRelatorioEdicaoComSucessoSemAutenticacao() {

        relatorioClient.listarCandidatosEdicaoSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }
}
