package br.com.dbccompany.vemser.captacao.steps;

import br.com.dbccompany.vemser.captacao.pages.LoginPage;
import br.com.dbccompany.vemser.captacao.utils.Manipulation;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();

    @E("preencho campo email de Login válido")
    public void preencherCampoEmailValido() {
        loginPage.preencherEmail(Manipulation.getProp().getProperty("prop.email"));
    }

    @E("não preencho campo email de Login")
    public void naoPreencherCampoEmail() {
        loginPage.preencherEmail("");
    }

    @E("preencho campo email de Login inválido")
    public void preencherCampoEmailInvalido() {
        loginPage.preencherEmail("teste.invalido");
    }

    @E("preencho campo senha de Login válido")
    public void preencherCampoSenhaValido() {
        loginPage.preencherSenha(Manipulation.getProp().getProperty("prop.senha"));
    }

    @E("não preencho campo senha de Login")
    public void naoPreencherCampoSenha() {
        loginPage.preencherSenha("");
    }

    @E("preencho campo senha de Login inválido")
    public void preencherCampoSenhaInvalido() {
        loginPage.preencherSenha("123");
    }

    @Quando("clico em 'LOGIN'")
    public void clicarBotaoLogin() {
        loginPage.clicarBotaoLogin();
    }

    @Entao("devo ser redirecionado para a página de Candidatos")
    public void validarPaginaCandidatos() {
        //assertTrue(loginPage.validarUrlAtual().contains("candidatos"));
        assertTrue(loginPage.validarPagina().contains("Candidatos"));
    }

    @Entao("devo visualizar mensagens de erro para campos vazios na tela Login")
    public void validarMensagensDeErroParaCamposVazios() {
    }

    @Entao("devo visualizar mensagen de erro para campo email vazio")
    public void validarMensagensDeErroParaEmailVazio() {
    }

    @Entao("devo visualizar mensagen de erro para campo senha vazio")
    public void validarMensagensDeErroParaSenhaVazio() {
    }

    @Entao("devo visualizar mensagen de erro para campo email inválido")
    public void validarMensagensDeErroParaEmailInvalido() {
    }

    @Entao("devo visualizar mensagen de erro para campo senha inválido")
    public void validarMensagensDeErroParaSenhaInvalido() {
    }

}
