package br.com.dbccompany.vemser.tests.entrevista;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.EntrevistaDataFactory;
import io.qameta.allure.Feature;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import models.entrevista.EntrevistaListaResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;
import service.EntrevistaService;

@Feature("Lista entrevista por mês")
public class ListarEntrevistaPorMesTest extends BaseTest {
    private static CandidatoService candidatoService = new CandidatoService();
    private static EntrevistaDataFactory entrevistaDataFactory = new EntrevistaDataFactory();
    private static EntrevistaService entrevistaService = new EntrevistaService();


    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista trilha por mês com sucesso")
    public void testListarEntrevistasPorMesComSucesso() {

        Integer mesEntrevista = 3;
        Integer anoEntrevista = 2025;

        CandidatoCriacaoResponseModel candidatoCriado = candidatoService.criarECadastrarCandidatoComCandidatoEntity()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;

        EntrevistaCriacaoModel entrevistaCriada = entrevistaDataFactory.entrevistaValidaComDataEspecifica(anoEntrevista, mesEntrevista, emailDoCandidato, candidatoAvaliado);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaService.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);


        EntrevistaListaResponseModel listaDeEntrevistas = entrevistaService.listarTodasAsEntrevistasPorMes(anoEntrevista, mesEntrevista)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaListaResponseModel.class);

        var deletarEntrevista = entrevistaService.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertNotNull(listaDeEntrevistas);
        Assertions.assertEquals(mesEntrevista, listaDeEntrevistas.getElementos().get(0).getDataEntrevista().getMonthValue());
        Assertions.assertEquals(anoEntrevista, listaDeEntrevistas.getElementos().get(0).getDataEntrevista().getYear());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 quando lista trilha por mês sem estar autenticado")
    public void testListarEntrevistasPorMesSemEstarAutenticado() {

        Integer mesEntrevista = 3;
        Integer anoEntrevista = 2025;


        var response = entrevistaService.listarTodasAsEntrevistasPorMes(anoEntrevista, mesEntrevista)
                .then()
                    .statusCode(HttpStatus.SC_OK);

    }
}
