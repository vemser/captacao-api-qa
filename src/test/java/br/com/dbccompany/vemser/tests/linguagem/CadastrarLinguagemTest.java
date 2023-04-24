package br.com.dbccompany.vemser.tests.linguagem;

import br.com.dbccompany.vemser.tests.base.BaseTest;
import models.linguagem.LinguagemModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.LinguagemService;

public class CadastrarLinguagemTest extends BaseTest {

    private static LinguagemService linguagemService = new LinguagemService();

    @Test
    @DisplayName("Cenário 1: Deve retornar 201 ao cadastrar linguagem com sucesso")
    public void testCadastrarNovaLinguagemComSucesso() {

        String novaLinguagem = "LINGUAGEM_TESTE";

        LinguagemModel linguagemCadastrada = linguagemService.criarLinguagemPassandoNome(novaLinguagem)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(LinguagemModel.class);

        Assertions.assertNotNull(linguagemCadastrada);
        Assertions.assertEquals(novaLinguagem.toLowerCase(), linguagemCadastrada.getNome().toLowerCase());

        var response = linguagemService.deletarLinguagemPorId(linguagemCadastrada.getIdLinguagem())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar 403 ao cadastrar linguagem sem autenticação")
    public void testCadastrarNovaLinguagemSemAutenticacao() {

        String novaLinguagem = "Python";

        var erroLinguagemCadastrada = linguagemService.criarLinguagemPassandoNomeSemAutenticacao(novaLinguagem)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

    }
}
