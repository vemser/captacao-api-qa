package br.com.dbccompany.vemser.tests.entrevista;

import client.EdicaoClient;
import client.EntrevistaClient;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listagem de entrevistas")
class ListarEntrevistaTest {
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();
    private static final String PATH_SCHEMA_LISTAR_ENTREVISTAS = "schemas/entrevista/listar_entrevistas.json";
    private static final EdicaoClient edicaoClient = new EdicaoClient();
	String edicao;

	@BeforeEach
	void setup() {
		edicao = edicaoClient.obterEdicaoAtual()
				.then()
					.extract().asString();
	}

	@Test
    @DisplayName("Cenário 1: Validação de contrato de listar entrevistas")
    @Tag("Contract")
    public void testValidarContratoListarEntrevistas() {

        entrevistaClient.listarTodasAsEntrevistas(edicao)
                .then()
					.body(matchesJsonSchemaInClasspath(PATH_SCHEMA_LISTAR_ENTREVISTAS));
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 200 quando lista as entrevistas cadastradas com sucesso")
    @Tag("Regression")
    void testListarEntrevistasCadastradasComSucesso() {

        var listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistas(edicao)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel[].class);

        Assertions.assertNotNull(listaDeEntrevistas);
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 quando lista as entrevistas sem estar autenticado")
    @Tag("Regression")
    void testListarEntrevistasSemAutenticacao() {

        entrevistaClient.listarTodasAsEntrevistasSemAutenticacao()
                .then()
					.statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
