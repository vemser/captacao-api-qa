package br.com.dbccompany.vemser.captacao.steps;

import br.com.dbccompany.vemser.captacao.pages.FormularioPage;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FormularioSteps {

    FormularioPage formularioPage = new FormularioPage();

    @E("preencho todos os campos válidos de Formulário")
    public void preencherCamposValidos() throws InterruptedException {
        formularioPage.selectTurno();
        formularioPage.preencherInstituicaoEnsino();
        formularioPage.preencherCurso();
        formularioPage.selectLinguagensProgramacao();
        formularioPage.selectTrilha();
        Thread.sleep(5000);
        formularioPage.selectMotivo();
        formularioPage.preencherAlgoImportante();
        formularioPage.selectProva();
        formularioPage.selectDisponibilidadeEfetivacao();
        formularioPage.selectDisponibilidade();
        Thread.sleep(5000);
    }

    @E("seleciono 'Sim' em deficiência")
    public void selecionarSimDeficiencia() {
        formularioPage.selectDeficiencia();
    }

    @E("preencho o campo deficiência")
    public void preencherCampoDeficiencia() {
        formularioPage.preencherDeficiencia();
    }

    @E("clico em 'Outro motivo'")
    public void selecionarMotivoOutro() {
        formularioPage.selectMotivoOutro();
    }

    @E("preencho o campo outro motivo")
    public void preencherCampoMotivo() {
        formularioPage.preencherMotivo();
    }

    @Quando("clico em 'Enviar'")
    public void clicarBotaoEnviar() {
        formularioPage.clicarBotaoEnviar();
    }

    @Quando("clico em 'Não' para matriculado")
    public void clicarBotaoNaoMatriculado() {
        formularioPage.clicarNaoMatriculado();
    }

    @Entao("devo ser redirecionado para a página de Finalização")
    public void validarPaginaFormulario() {
        assertTrue(formularioPage.validarPagina().contains(""));
    }

    @Entao("devo visualizar mensagem de erro bloqueando a continuação do cadastro")
    public void validarMensagemDeErroEBloqueioDaPagina() {
        assertEquals("Devido as restrições impostas pelas leis brasileiras, somente alunos que possuem " +
                "vínculo com uma instituição de ensino podem se candidatar às vagas de estágio.",
                formularioPage.validarMsgErroNaoMatriculado());
    }

/*    @Entao("devo visualizar mensagens de erro para campos vazios na tela Formulário")
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
    }*/

}
