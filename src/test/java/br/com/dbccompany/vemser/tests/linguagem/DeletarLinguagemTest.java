package br.com.dbccompany.vemser.tests.linguagem;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.linguagem.LinguagemModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.LinguagemService;

import java.util.Arrays;
import java.util.List;

@DisplayName("Endpoint de remoção de linguagens")
public class DeletarLinguagemTest extends BaseTest {

    private static LinguagemService linguagemService = new LinguagemService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 204 ao deletar uma linguagem com sucesso")
    public void testDeletarLinguagemComSucesso() {

        String novaLinguagem = "LINGUAGEM_TESTE";

        LinguagemModel linguagemCadastrada = linguagemService.criarLinguagemPassandoNome(novaLinguagem)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(LinguagemModel.class);

        var response = linguagemService.listarLinguagens()
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

        var linguagemDeletada = linguagemService.deletarLinguagemPorId(linguagemCadastrada.getIdLinguagem())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

        var responseDelete = linguagemService.listarLinguagens()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(LinguagemModel[].class);

        List<LinguagemModel> listaDeLinguagensAposDelecao = Arrays.stream(responseDelete).toList();

        Boolean linguagemDeletadaNaoEstaListada = true;

        for (int i = 0; i < listaDeLinguagensAposDelecao.size(); i++) {
            if (listaDeLinguagensAposDelecao.get(i).getNome().toLowerCase().equals(novaLinguagem.toLowerCase())) {
                linguagemDeletadaNaoEstaListada = false;
            }
        }

        Assertions.assertTrue(linguagemCadastradaEstaListada);
        Assertions.assertTrue(linguagemDeletadaNaoEstaListada);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao deletar uma linguagem sem autenticação")
    public void testDeletarLinguagemSemAutenticacao() {

        var response = linguagemService.listarLinguagens()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(LinguagemModel[].class);

        List<LinguagemModel> listaDeLinguagens = Arrays.stream(response).toList();

        LinguagemModel linguagem = listaDeLinguagens.get(0);

        var linguagemDeletada = linguagemService.deletarLinguagemPorIdSemAutenticacao(linguagem.getIdLinguagem())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
