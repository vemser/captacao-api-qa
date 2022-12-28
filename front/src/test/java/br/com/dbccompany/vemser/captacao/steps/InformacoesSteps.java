package br.com.dbccompany.vemser.captacao.steps;

import br.com.dbccompany.vemser.captacao.pages.InformacoesPage;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InformacoesSteps {

    InformacoesPage informacoesPage = new InformacoesPage();

    @E("preencho todos os campos válidos de Informações")
    public void preencherCamposValidos() {
        informacoesPage.preencherCampoNomeCompleto("Nome Sobrenome");
        informacoesPage.preencherCampoEmail("nome.sobrenome@gmail.com");
        informacoesPage.preencherCampoRG("0123456789");
        informacoesPage.preencherCampoCPF("01234567890");
        informacoesPage.preencherCampoTelefone("(71) 99999-9999");
        informacoesPage.preencherCampoDataDeNascimento("19101993");
        informacoesPage.preencherCampoCidade("Cidade");
    }

    @E("preencho o campo nome completo inválido")
    public void preencherCampoNomeCompletoInvalido() {
        informacoesPage.preencherCampoNomeCompleto("Nome");
    }

    @E("preencho o campo email inválido")
    public void preencherCampoEmailInvalido() {
        informacoesPage.preencherCampoEmail("email");
    }

    @E("preencho o campo rg inválido")
    public void preencherCampoRgInvalido() {
        informacoesPage.preencherCampoRG("1313817238916745196071993740750");
    }

    @E("preencho o campo cpf inválido")
    public void preencherCampoCpfInvalido() {
        informacoesPage.preencherCampoCPF("123");
    }

    @E("preencho o campo telefone inválido")
    public void preencherCampoTelefoneInvalido() {
        informacoesPage.preencherCampoTelefone("123");
    }

    @E("preencho o campo data de nascimento inválido")
    public void preencherCampoDataNascimentoInvalido() {
        informacoesPage.preencherCampoDataDeNascimento("01012020");
    }

    @Quando("clico em 'Próximo'")
    public void clicarBotaoProximo() {
        informacoesPage.clicarBotaoProximo();
    }

    @Entao("devo ser redirecionado para a página de Formulário")
    public void validarPaginaFormulario() {
        assertTrue(informacoesPage.validarPagina()
                .contains("Você é matriculado em algum curso de graduação ou técnico?"));
    }

    @Entao("devo visualizar mensagens de erro para campos vazios na tela Formulário")
    public void validarMensagensDeErroCamposObrigatoriosVazios() {
        assertEquals("O nome é obrigatório", informacoesPage.validarMsgErroNomeCompleto());
        assertEquals("O email é obrigatório", informacoesPage.validarMsgErroEmail());
        assertEquals("O RG é obrigatório", informacoesPage.validarMsgErroRg());
        assertEquals("O CPF é obrigatório", informacoesPage.validarMsgErroCpf());
        assertEquals("O telefone é obrigatório",
                informacoesPage.validarMsgErroTelefone());
        assertEquals("A data de nascimento deve ser uma data válida",
                informacoesPage.validarMsgErroDataNascimento());
        assertEquals("A cidade é obrigatória", informacoesPage.validarMsgErroCidade());
    }

    @Entao("devo visualizar mensagem de erro para nome completo inválido")
    public void validarMensagemDeErroCampoNomeCompletoInvalido() {
        assertEquals("O nome deve ter no mínimo sobrenome", informacoesPage.validarMsgErroNomeCompleto());
    }

    @Entao("devo visualizar mensagem de erro para email inválido")
    public void validarMensagemDeErroCampoEmailInvalido() {
        assertEquals("O email deve ser um endereço de email válido", informacoesPage.validarMsgErroEmail());
    }

/*    @Entao("devo visualizar mensagem de erro para rg inválido")
    public void validarMensagemDeErroCampoEmailInvalido() {
        assertEquals("O email deve ser um endereço de email válido", informacoesPage.validarMsgErroEmail());
    }*/

    @Entao("devo visualizar mensagem de erro para cpf inválido")
    public void validarMensagemDeErroCampoCpfInvalido() {
        assertEquals("O CPF deve ser um número válido", informacoesPage.validarMsgErroCpf());
    }

    @Entao("devo visualizar mensagem de erro para telefone inválido")
    public void validarMensagemDeErroCampoTelefoneInvalido() {
        assertEquals("O telefone deve ser um número válido no formato (99)99999-9999",
                informacoesPage.validarMsgErroTelefone());
    }

    @Entao("devo visualizar mensagem de erro para idade inválida")
    public void validarMensagemDeErroCampoDataNascimentoInvalido() {
        assertEquals("A data de nascimento deve ter no mínimo 16 anos", informacoesPage.validarMsgErroTelefone());
    }

}
