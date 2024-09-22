package br.com.dbccompany.vemser.tests.disponibilidade;

import client.disponibilidade.DisponibilidadeClient;
import models.disponibilidade.DisponibilidadeResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import utils.auth.Auth;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de listar disponibilidades")
public class ListarDisponibilidadesTest {

    private final DisponibilidadeClient disponibilidadeClient = new DisponibilidadeClient();
    public Integer idGestor = 1;

    @BeforeEach
    void setUp() {
        Auth.usuarioGestaoDePessoas();
    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 1: Validar contrato listar disponibilidade com sucesso")
    public void testValidarContratoListarDisponibilidade() {

        disponibilidadeClient.listarDisponibilidades()
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/disponibilidade/listarDisponibilidade.json"));
    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 2: Validar listar disponibilidade com sucesso")
    public void testListarDisponibilidadeComSucesso() {
        List<DisponibilidadeResponseModel> listaDisponibilidade = disponibilidadeClient.listarDisponibilidades()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .jsonPath()
                .getList("$", DisponibilidadeResponseModel.class);

        Assertions.assertNotNull(listaDisponibilidade, "A lista de disponibilidade não deve ser nula");
        Assertions.assertFalse(listaDisponibilidade.isEmpty(), "A lista de disponibilidade não deve estar vazia");

    }

    @Test
    @Tag("Regression")
    @DisplayName("Cenário 3: Tentar listar disponibilidade sem estar autenticado")
    public void testListarDisponibilidadeSemAutenticacao() {
        disponibilidadeClient.listarDisponibilidadesSemAutenticacao()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
