package br.com.dbccompany.vemser.tests.entrevista;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import dataFactory.EntrevistaDataFactory;
import models.candidato.CandidatoCriacaoResponseModel;
import models.entrevista.EntrevistaCriacaoModel;
import models.entrevista.EntrevistaCriacaoResponseModel;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CandidatoService;
import service.EntrevistaService;
import utils.Email;

import java.util.Locale;

@DisplayName("Endpoint de listagem de entrevistas por email")
public class ListarEntrevistaPorEmailTest extends BaseTest {

    private static CandidatoService candidatoService = new CandidatoService();
    private static EntrevistaDataFactory entrevistaDataFactory = new EntrevistaDataFactory();
    private static EntrevistaService entrevistaService = new EntrevistaService();
    private static Faker faker = new Faker(new Locale("pt-BR"));


    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao buscar entrevista por email do candidato com sucesso")
    public void testListaEntrevistaPorEmailComSucesso() {
        String email = Email.getEmail();

        CandidatoCriacaoResponseModel candidatoCriado = candidatoService.criarECadastrarCandidatoComCandidatoEntityEEmailEspecifico(email)
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

        EntrevistaCriacaoResponseModel entrevista = entrevistaService.listarTodasAsEntrevistasPorEmail(emailDoCandidato)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(EntrevistaCriacaoResponseModel.class);

        var deletarEntrevista = entrevistaService.deletarEntrevistaPorId(entrevistaCadastrada.getIdEntrevista())
                        .then()
                                .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertNotNull(entrevista);
        Assertions.assertEquals(emailDoCandidato, entrevista.getCandidatoEmail());
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao buscar entrevista por email do candidato sem autenticação")
    public void testListaEntrevistaPorEmailSemAutenticacao() {
        String emailDoCandidato = Email.getEmail();

        var response = entrevistaService.listarTodasAsEntrevistasPorEmailSemAutenticacao(emailDoCandidato)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
