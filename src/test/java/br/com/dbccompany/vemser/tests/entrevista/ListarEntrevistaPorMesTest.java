package br.com.dbccompany.vemser.tests.entrevista;

import client.entrevista.EntrevistaClient;
import models.entrevista.EntrevistaListaResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de entrevistas por mês")
class ListarEntrevistaPorMesTest {

    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final String PATH_SCHEMA_LISTAR_ENTREVISTAS_POR_MES = "schemas/entrevista/listar_entrevistas_por_mes.json";
	private static final Integer MES_ENTREVISTA = 9;
	private static final Integer ANO_ENTREVISTA = 2025;

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar entrevistas por mês")
    @Tag("Contract")
    public void testValidarContratoListarEntrevistasPorMes() {

        entrevistaClient.listarTodasAsEntrevistasPorMes(ANO_ENTREVISTA, MES_ENTREVISTA)
                .then()
               		 .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_ENTREVISTAS_POR_MES))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando lista trilha por mês com sucesso")
    @Tag("Regression")
    void testListarEntrevistasPorMesComSucesso() {

        EntrevistaListaResponseModel listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistasPorMes(ANO_ENTREVISTA, MES_ENTREVISTA)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaListaResponseModel.class);

        Assertions.assertNotNull(listaDeEntrevistas);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 quando lista trilha por mês sem estar autenticado")
    @Tag("Regression")
    void testListarEntrevistasPorMesSemEstarAutenticado() {

        entrevistaClient.listarTodasAsEntrevistasPorMesSemAutenticacao(ANO_ENTREVISTA, MES_ENTREVISTA)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
