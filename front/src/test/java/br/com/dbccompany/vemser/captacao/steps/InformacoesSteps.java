package br.com.dbccompany.vemser.captacao.steps;

import br.com.dbccompany.vemser.captacao.pages.InformacoesPage;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static org.junit.Assert.assertTrue;

public class InformacoesSteps {

    InformacoesPage informacoesPage = new InformacoesPage();

    @E("preencho todos os campos válidos de Informações")
    public void preencherCamposValidos() {
        informacoesPage.preencherCampoNomeCompleto("Nome Sobrenome");
        informacoesPage.preencherCampoEmail("nome.sobrenome@gmail.com");
        informacoesPage.preencherCampoRG("");
        informacoesPage.preencherCampoCPF("");
        informacoesPage.preencherCampoTelefone("(71) 99999-9999");
        informacoesPage.preencherCampoDataDeNascimento("");
        informacoesPage.preencherCampoCidade("Cidade");
    }

    @Quando("clico em 'Próximo'")
    public void clicarBotaoProximo() {
        informacoesPage.clicarBotaoProximo();
    }

    @Entao("devo ser redirecionado para a página de Formulário")
    public void validarUrlPaginaFormulario() {
        assertTrue(informacoesPage.validarUrlAtual().contains("subscription"));
    }

}
