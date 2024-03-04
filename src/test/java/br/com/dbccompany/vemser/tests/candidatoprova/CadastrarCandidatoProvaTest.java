package br.com.dbccompany.vemser.tests.candidatoprova;

import client.candidatoprova.CandidatoProvaClient;
import factory.candidatoprova.CandidatoProvaDataFactory;
import models.candidatoprova.CandidatoProvaModel;
import models.candidatoprova.CandidatoProvaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Endpoint de cadastrar prova-cadastro")
public class CadastrarCandidatoProvaTest {

    private static final CandidatoProvaClient candidatoProvaClient = new CandidatoProvaClient();
    private static final CandidatoProvaDataFactory candidatoProvaDataFactory = new CandidatoProvaDataFactory();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando cadastrar candidato-prova com sucesso")
    void testCadastrarCandidatoProvaComSucesso() {

        CandidatoProvaModel candidatoProva = candidatoProvaDataFactory.novoCandidatoProva();

        CandidatoProvaResponse candidatoProvaResponse = candidatoProvaClient.cadastrarCandidatoProva(candidatoProva)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoProvaResponse.class)
                ;

        assertAll(
                () -> Assertions.assertEquals("Cadastro realizado com sucesso", candidatoProvaResponse.getMensagem()),
                () -> Assertions.assertNotNull(candidatoProvaResponse.getId())
        );
    }

}
