package br.com.dbccompany.vemser.tests.relatorio;

import client.relatorio.RelatorioClient;
import models.relatorio.RelatorioEtniaModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint para emissão de relatório de etnias")
class RelatorioEtniaTest {

    private static final RelatorioClient relatorioClient = new RelatorioClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar com sucesso relatório de candidato por etnia")
    void testListarRelatorioEtniaComSucesso() {

        var response = relatorioClient.listarCandidatosEtnia()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(RelatorioEtniaModel[].class);

        List<RelatorioEtniaModel> listaRelatorioEtnia = Arrays.stream(response).toList();

        if (!listaRelatorioEtnia.isEmpty()) {
            for (RelatorioEtniaModel r : listaRelatorioEtnia) {
                Assertions.assertNotNull(r.getEtnia());
            }
        }
    }

    @Test
    @DisplayName("Cenário 3: Deve retornar 403 ao listar relatório de candidato por etnia sem autenticação")
    void testListarRelatorioEtniaComSucessoSemAutenticacao() {

        var response = relatorioClient.listarCandidatosEtniaSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
