package br.com.dbccompany.vemser.tests.candidato;

import client.candidato.CandidatoClient;
import models.candidato.CandidatoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Endpoint de busca de candidato")
class ListarCandidatoTest {

    private static final CandidatoClient candidatoClient = new CandidatoClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando busca a lista de candidatos no sistema")
    void testListarCandidatosComSucesso() {

        CandidatoModel listaCandidato =
            candidatoClient.listarTodosOsCandidatos(0, 20)
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                    .as(CandidatoModel.class);

        Assertions.assertNotNull(listaCandidato.getTotalElementos());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getNome());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getDataNascimento());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEmail());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getTelefone());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getRg());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getCpf());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEstado());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getCidade());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getPcd());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getAtivo());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getLinguagens());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEdicao().getIdEdicao());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEdicao().getNome());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getEdicao().getNotaCorte());
        Assertions.assertNotNull(listaCandidato.elementos.get(0).getFormulario());
    }

    @Test
    @DisplayName("Cenário 2: Deve validar o contrato de listagem de candidatos no sistema")
    void testValidarContratoListarCandidatos() {

        candidatoClient.listarTodosOsCandidatos(0, 20)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/ListarCandidato.json"));
    }
}
