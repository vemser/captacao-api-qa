package br.com.dbccompany.vemser.tests.relatorio;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.relatorio.RelatorioEtniaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.RelatorioService;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint para emissão de relatório de etnias")
public class RelatorioEtniaTest extends BaseTest {

    private static RelatorioService relatorioService = new RelatorioService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar com sucesso relatório de candidato por etnia")
    public void testListarRelatorioEtniaComSucesso() {

        var response = relatorioService.listarCandidatosEtnia()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEtniaModel[].class);

        List<RelatorioEtniaModel> listaRelatorioEtnia = Arrays.stream(response).toList();

        if (listaRelatorioEtnia.size() != 0) {
            for (RelatorioEtniaModel r : listaRelatorioEtnia) {
                Assertions.assertNotNull(r.getEtnia());
            }
        }
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 ao listar relatório de candidato por etnia sem autenticação")
    public void testListarRelatorioEtniaComSucessoSemAutenticacao() {

        var response = relatorioService.listarCandidatosEtniaSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
