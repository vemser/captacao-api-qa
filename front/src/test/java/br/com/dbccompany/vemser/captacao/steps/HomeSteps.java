package br.com.dbccompany.vemser.captacao.steps;

import br.com.dbccompany.vemser.captacao.pages.HomePage;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static org.junit.Assert.assertTrue;

public class HomeSteps {

    HomePage homePage = new HomePage();

    @Dado("que estou na página inicial")
    public void validarPaginaInicial() {
        assertTrue(homePage.validarUrlAtual().contains("captacao"));
    }

    @Quando("clico em ‘Inscrição’")
    public void clicarBotaoInscricao() {
        homePage.clicarBotaoInscricao();
    }

    @Entao("devo ser redirecionado para a página de Informações")
    public void validarUrlPaginaInscricao() {
        assertTrue(homePage.validarUrlAtual().contains("subscription"));
    }

    @Dado("que acesso a página de Informações")
    public void validarPaginaInscricaoInformacoes() {
        homePage.clicarBotaoInscricao();
        assertTrue(homePage.validarUrlAtual().contains("subscription"));
    }

}
