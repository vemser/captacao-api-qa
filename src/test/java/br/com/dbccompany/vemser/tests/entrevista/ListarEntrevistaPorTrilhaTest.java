package br.com.dbccompany.vemser.tests.entrevista;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import factory.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.CandidatoClient;
import client.EntrevistaClient;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Endpoint de listagem de entrevistas por trilha")
public class ListarEntrevistaPorTrilhaTest extends BaseTest {

    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static EntrevistaDataFactory entrevistaDataFactory = new EntrevistaDataFactory();
    private static EntrevistaClient entrevistaClient = new EntrevistaClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 quando lista entrevistas de trilhas existentes")
    public void testListarEntrevistasPorTrilhaComSucesso() {
        String trilha = "QA";

        CandidatoCriacaoResponseModel candidatoCriado = candidatoClient.criarECadastrarCandidatoComCandidatoEntityETrilhaEspecifica(trilha)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(CandidatoCriacaoResponseModel.class);

        String emailDoCandidato = candidatoCriado.getEmail();
        Boolean candidatoAvaliado = true;
        Integer idTrilha = candidatoCriado.getFormulario().getTrilhas().get(0).getIdTrilha();

        EntrevistaCriacaoModel entrevistaCriada = entrevistaDataFactory.entrevistaCriacaoValida(emailDoCandidato, candidatoAvaliado, idTrilha);

        EntrevistaCriacaoResponseModel entrevistaCadastrada = entrevistaClient.cadastrarEntrevista(entrevistaCriada)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);


        var lista = entrevistaClient.listarTodasAsEntrevistasPorTrilha(trilha)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel[].class);

        List<EntrevistaCriacaoResponseModel> listaDeEntrevistas = Arrays.stream(lista).toList();

        var deletarEntrevista = entrevistaClient.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);


        Assertions.assertNotNull(listaDeEntrevistas);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 404 quando lista entrevistas de trilhas não existentes")
    public void testListarEntrevistasPorTrilhaNaoExistente() {
        String trilhaNaoExistente = "-*/-*/-*/-*/-*/";

        var lista = entrevistaClient.listarTodasAsEntrevistasPorTrilha(trilhaNaoExistente)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(equalTo("[]"));
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 quando lista entrevistas de trilhas sem autenticação")
    public void testListarEntrevistasPorTrilhaSemAutenticacao() {
        String trilha = "QA";

        var lista = entrevistaClient.listarTodasAsEntrevistasPorTrilhaSemAutenticacao(trilha)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
