package br.com.dbccompany.vemser.tests.linguagem;

import client.linguagem.LinguagemClient;
import models.linguagem.LinguagemModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de listagem de linguagens")
class ListarLinguagemTest  {

    private static final LinguagemClient linguagemClient = new LinguagemClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar linguagens com sucesso")
    void testListarLinguagensComSucesso() {

        String novaLinguagem = "LINGUAGEM_TESTE";

        LinguagemModel linguagemCadastrada = linguagemClient.criarLinguagemPassandoNome(novaLinguagem)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(LinguagemModel.class);

        var response = linguagemClient.listarLinguagens()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(LinguagemModel[].class);

        List<LinguagemModel> listaDeLinguagens = Arrays.stream(response).toList();

        boolean linguagemCadastradaEstaListada = false;

        for (LinguagemModel linguagem : listaDeLinguagens) {
            if (linguagem.getNome().equalsIgnoreCase(novaLinguagem)) {
                linguagemCadastradaEstaListada = true;
                break; // Terminar o loop após a condição ser atendida
            }
        }

        var linguagemDeletada = linguagemClient.deletarLinguagemPorId(linguagemCadastrada.getIdLinguagem())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertTrue(linguagemCadastradaEstaListada);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao listar linguagens sem autenticação")
    void testListarLinguagensSemAutenticacao() {

        var response = linguagemClient.listarLinguagensSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }
}
