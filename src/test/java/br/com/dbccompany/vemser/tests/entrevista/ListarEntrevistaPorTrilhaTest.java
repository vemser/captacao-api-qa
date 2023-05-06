package br.com.dbccompany.vemser.tests.entrevista;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;
import service.EntrevistaService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Endpoint de listagem de entrevistas por trilha")
public class ListarEntrevistaPorTrilhaTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static EntrevistaDataFactory entrevistaDataFactory = new EntrevistaDataFactory();
    private static EntrevistaService entrevistaService = new EntrevistaService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista entrevistas de trilhas existentes")
    public void testListarEntrevistasPorTrilhaComSucesso() {
        String trilha = "QA";

        CandidatoCriacaoResponseModel candidatoCriado = candidatoService.criarECadastrarCandidatoComCandidatoEntityETrilhaEspecifica(trilha)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;
        Integer idTrilha = candidatoCriado.getFormulario().getTrilhas().get(0).getIdTrilha();

        EntrevistaCriacaoModel entrevistaCriada = entrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado, idTrilha);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaService.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);


        var lista = entrevistaService.listarTodasAsEntrevistasPorTrilha(trilha)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel[].class);

        List<EntrevistaCriacaoResponseModel> listaDeEntrevistas = Arrays.stream(lista).toList();

        var deletarEntrevista = entrevistaService.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);


        Assertions.assertNotNull(listaDeEntrevistas);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 quando lista entrevistas de trilhas não existentes")
    public void testListarEntrevistasPorTrilhaNaoExistente() {
        String trilhaNaoExistente = "-*/-*/-*/-*/-*/";

        var lista = entrevistaService.listarTodasAsEntrevistasPorTrilha(trilhaNaoExistente)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(equalTo("[]"));
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 quando lista entrevistas de trilhas sem autenticação")
    public void testListarEntrevistasPorTrilhaSemAutenticacao() {
        String trilha = "QA";

        var lista = entrevistaService.listarTodasAsEntrevistasPorTrilhaSemAutenticacao(trilha)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
