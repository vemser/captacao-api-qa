package br.com.dbccompany.vemser.tests.linguagem;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.linguagem.LinguagemModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import client.LinguagemClient;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de listagem de linguagens")
public class ListarLinguagemTest extends BaseTest {

    private static LinguagemClient linguagemClient = new LinguagemClient();

    @Test
    @DisplayName("Cenário 1: Deve retornar 200 ao listar linguagens com sucesso")
    public void testListarLinguagensComSucesso() {

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

        Boolean linguagemCadastradaEstaListada = false;

        for (int i = 0; i < listaDeLinguagens.size(); i++) {
            if (listaDeLinguagens.get(i).getNome().toLowerCase().equals(novaLinguagem.toLowerCase())) {
                linguagemCadastradaEstaListada = true;
            }
        }

        var linguagemDeletada = linguagemClient.deletarLinguagemPorId(linguagemCadastrada.getIdLinguagem())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertTrue(linguagemCadastradaEstaListada);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao listar linguagens sem autenticação")
    public void testListarLinguagensSemAutenticacao() {

        var response = linguagemClient.listarLinguagensSemAutenticacao()
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }
}
