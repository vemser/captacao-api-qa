package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
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
    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final String PATH_SCHEMA_LISTAR_ENTREVISTAS_POR_MES = "schemas/entrevista/listar_entrevistas_por_mes.json";

    @Test
    @DisplayName("Cenário 1: Validação de contrato de listar entrevistas por trilha")
    public void testValidarContratoListarEntrevistasPorMes() {

        Integer mesEntrevista = 9;
        Integer anoEntrevista = 2025;

        entrevistaClient.listarTodasAsEntrevistasPorMes(anoEntrevista, mesEntrevista)
                .then()
                .body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_ENTREVISTAS_POR_MES))
        ;
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando lista trilha por mês com sucesso")
    @Tag("Regression")
    void testListarEntrevistasPorMesComSucesso() {

        Integer mesEntrevista = 9;
        Integer anoEntrevista = 2025;


        EntrevistaListaResponseModel listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistasPorMes(anoEntrevista, mesEntrevista)
                .then()
				.log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaListaResponseModel.class);

        Assertions.assertNotNull(listaDeEntrevistas);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 quando lista trilha por mês sem estar autenticado")
    @Tag("Regression")
    void testListarEntrevistasPorMesSemEstarAutenticado() {

        Integer mesEntrevista = 3;
        Integer anoEntrevista = 2025;

        entrevistaClient.listarTodasAsEntrevistasPorMesSemAutenticacao(anoEntrevista, mesEntrevista)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
