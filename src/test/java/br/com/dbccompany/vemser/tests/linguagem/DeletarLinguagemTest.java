
/*
 * Conforme ficou alinhado com o time, o teste de cadastro de linguagem foi comentado, pois o mesmo não está sendo utilizado na API.
 * */



//package br.com.dbccompany.vemser.tests.linguagem;
//
//import client.LinguagemClient;
//import models.linguagem.LinguagemModel;
//import org.apache.http.HttpStatus;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//@DisplayName("Endpoint de remoção de linguagens")
//class DeletarLinguagemTest {
//
//    private static final LinguagemClient linguagemClient = new LinguagemClient();
//
//    @Test
//    @DisplayName("Cenário 1: Deve retornar 204 ao deletar uma linguagem com sucesso")
//    void testDeletarLinguagemComSucesso() {
//
//        String novaLinguagem = "LINGUAGEM_TESTE";
//
//        LinguagemModel linguagemCadastrada = linguagemClient.criarLinguagemPassandoNome(novaLinguagem)
//                .then()
//                    .statusCode(HttpStatus.SC_CREATED)
//                    .extract()
//                    .as(LinguagemModel.class);
//
//        var response = linguagemClient.listarLinguagens()
//                .then()
//                    .statusCode(HttpStatus.SC_OK)
//                    .extract()
//                    .as(LinguagemModel[].class);
//
//        List<LinguagemModel> listaDeLinguagens = Arrays.stream(response).toList();
//
//        boolean linguagemCadastradaEstaListada = false;
//
//        for (LinguagemModel linguagem : listaDeLinguagens) {
//            if (linguagem.getNome().equalsIgnoreCase(novaLinguagem)) {
//                linguagemCadastradaEstaListada = true;
//                break; // Terminar o loop após a condição ser atendida
//            }
//        }
//
//
//        var linguagemDeletada = linguagemClient.deletarLinguagemPorId(linguagemCadastrada.getIdLinguagem())
//                .then()
//                    .statusCode(HttpStatus.SC_NO_CONTENT);
//
//        var responseDelete = linguagemClient.listarLinguagens()
//                .then()
//                    .statusCode(HttpStatus.SC_OK)
//                    .extract()
//                    .as(LinguagemModel[].class);
//
//        List<LinguagemModel> listaDeLinguagensAposDelecao = Arrays.stream(responseDelete).toList();
//
//        boolean linguagemDeletadaNaoEstaListada = true;
//
//        for (LinguagemModel linguagemModel : listaDeLinguagensAposDelecao) {
//            if (linguagemModel.getNome().equalsIgnoreCase(novaLinguagem)) {
//                linguagemDeletadaNaoEstaListada = false;
//                break; // Terminar o loop após a condição ser atendida
//            }
//        }
//
//
//        Assertions.assertTrue(linguagemCadastradaEstaListada);
//        Assertions.assertTrue(linguagemDeletadaNaoEstaListada);
//    }
//
//    @Test
//    @DisplayName("Cenário 2: Deve retornar 403 ao deletar uma linguagem sem autenticação")
//    void testDeletarLinguagemSemAutenticacao() {
//
//        var response = linguagemClient.listarLinguagens()
//                .then()
//                    .statusCode(HttpStatus.SC_OK)
//                    .extract()
//                    .as(LinguagemModel[].class);
//
//        List<LinguagemModel> listaDeLinguagens = Arrays.stream(response).toList();
//
//        LinguagemModel linguagem = listaDeLinguagens.get(0);
//
//        var linguagemDeletada = linguagemClient.deletarLinguagemPorIdSemAutenticacao(linguagem.getIdLinguagem())
//                .then()
//                    .statusCode(HttpStatus.SC_FORBIDDEN);
//    }
//}
