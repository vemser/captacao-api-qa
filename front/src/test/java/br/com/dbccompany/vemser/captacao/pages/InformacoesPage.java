package br.com.dbccompany.vemser.captacao.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class InformacoesPage extends BasePage {

    private static final By campoNomeCompleto = By.cssSelector("#step-1-nome");
    private static final By msgErroNomeCompleto = By.cssSelector("#step-1-nome-helper-text");
    private static final By campoEmail = By.cssSelector("#step-1-email");
    private static final By msgErroEmail = By.cssSelector("#step-1-email-helper-text");
    private static final By campoRG = By.cssSelector("#step-1-rg");
    private static final By msgErroRG = By.cssSelector("#step-1-rg-helper-text");
    private static final By campoCPF = By.cssSelector("#step-1-cpf");
    private static final By msgErroCPF = By.cssSelector("#step-1-cpf-helper-text");
    private static final By campoTelefone = By.cssSelector("#step-1-telefone");
    private static final By msgErroTelefone = By.cssSelector("#step-1-telefone-helper-text");
    private static final By campoDataNascimento = By.cssSelector("#step-1-dataNascimento");
    private static final By msgErroDataNascimento = By.cssSelector("#step-1-dataNascimento-helper-text");
    private static final By campoCidade = By.cssSelector("#step-1-cidade");
    private static final By msgErroCidade = By.cssSelector("#step-1-cidade-helper-text");
    private static final By selectEstado = By.cssSelector("#step-1-estado");
    private static final By selectNeurodiversidade = By.cssSelector("#step-1-neurodiversidade");
    private static final By btnProximo = By.cssSelector("#step-1-enviar");

    @Step("Preencher campo nome completo")
    public void preencherCampoNomeCompleto(String nomeCompleto) {
        sendKeys(campoNomeCompleto, nomeCompleto);
    }

    @Step("Preencher campo email")
    public void preencherCampoEmail(String email) {
        sendKeys(campoEmail, email);
    }

    @Step("Preencher campo rg")
    public void preencherCampoRG(String rg) {
        sendKeys(campoRG, rg);
    }

    @Step("Preencher campo cpf")
    public void preencherCampoCPF(String cpf) {
        click(campoCPF);
        sendKeys(campoCPF, cpf);
    }

    @Step("Preencher campo telefone")
    public void preencherCampoTelefone(String telefone) {
        click(campoTelefone);
        sendKeys(campoTelefone, telefone);
    }

    @Step("Preencher campo data de nascimento")
    public void preencherCampoDataDeNascimento(String dataNascimento) {
        click(campoDataNascimento);
        sendKeys(campoDataNascimento, dataNascimento);
    }

    @Step("Preencher campo cidade")
    public void preencherCampoCidade(String cidade) {
        sendKeys(campoCidade, cidade);
    }

    @Step("Clicar no botão Próximo")
    public void clicarBotaoProximo() {
        click(btnProximo);
    }

    @Step("Validar mensagem erro campo nome completo vazio ou inválido")
    public String validarMsgErroNomeCompleto(){
        return getText(msgErroNomeCompleto);
    }

    @Step("Validar mensagem erro campo email vazio ou inválido")
    public String validarMsgErroEmail(){
        return getText(msgErroEmail);
    }

    @Step("Validar mensagem erro campo rg vazio ou inválido")
    public String validarMsgErroRg(){
        return getText(msgErroRG);
    }

    @Step("Validar mensagem erro campo cpf vazio ou inválido")
    public String validarMsgErroCpf(){
        return getText(msgErroCPF);
    }

    @Step("Validar mensagem erro campo telefone vazio ou inválido")
    public String validarMsgErroTelefone(){
        return getText(msgErroTelefone);
    }

    @Step("Validar mensagem erro campo data de nascimento vazio ou inválido")
    public String validarMsgErroDataNascimento(){
        return getText(msgErroDataNascimento);
    }

    @Step("Validar mensagem erro campo cidade vazio ou inválido")
    public String validarMsgErroCidade(){
        return getText(msgErroCidade);
    }

    @Step("Validar página")
    public String validarPagina() {
        return getPageSource();
    }

    @Step("Validar url atual")
    public String validarUrlAtual() {
        return getCurrentUrl();
    }

}
