package br.com.dbccompany.vemser.tests.entrevista;

import client.candidato.CandidatoClient;
import client.entrevista.EntrevistaClient;
import factory.entrevista.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import models.entrevista.EntrevistaListaResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Endpoint de listagem de entrevistas por mês")
class ListarEntrevistaPorMesTest {
    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final EntrevistaClient entrevistaClient = new EntrevistaClient();


    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista trilha por mês com sucesso")
    void testListarEntrevistasPorMesComSucesso() {

        Integer mesEntrevista = 3;
        Integer anoEntrevista = 2025;

        CandidatoCriacaoResponseModel candidatoCriado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;
        Integer idTrilha = candidatoCriado.getFormulario().getTrilhas().get(0).getIdTrilha();

        EntrevistaCriacaoModel entrevistaCriada = EntrevistaDataFactory.entrevistaValidaComDataEspecifica(anoEntrevista,
                mesEntrevista, emailDoCandidato, candidatoAvaliado, idTrilha);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaClient.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);


        EntrevistaListaResponseModel listaDeEntrevistas = entrevistaClient.listarTodasAsEntrevistasPorMes(anoEntrevista, mesEntrevista)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaListaResponseModel.class);

        var deletarEntrevista = entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertNotNull(listaDeEntrevistas);
        Assertions.assertEquals(mesEntrevista, listaDeEntrevistas.getElementos().get(0).getDataEntrevista().getMonthValue());
        Assertions.assertEquals(anoEntrevista, listaDeEntrevistas.getElementos().get(0).getDataEntrevista().getYear());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando lista trilha por mês sem estar autenticado")
    void testListarEntrevistasPorMesSemEstarAutenticado() {

        Integer mesEntrevista = 3;
        Integer anoEntrevista = 2025;

        var response = entrevistaClient.listarTodasAsEntrevistasPorMes(anoEntrevista, mesEntrevista)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }
}
