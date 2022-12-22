package br.com.dbccompany.vemser.captacao.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class InformacoesPage extends BasePage {

    private static final By campoNomeCompleto = By.cssSelector("#step-1-nome");
    private static final By msgErroNomeCompleto = By.cssSelector("#step-1-nome-helper-text");
    private static final By campoEmail = By.cssSelector("#step-1-email");
    private static final By msgErroEmail = By.cssSelector("#step-1-email-helper-text");
    private static final By campoRG = By.cssSelector("#step-1-rg");
    //private static final By msgErrroRG = By.cssSelector("");
    private static final By campoCPF = By.cssSelector("#step-1-cpf");
    private static final By msgErroCPF = By.cssSelector("#step-1-cpf-helper-text");
    private static final By campoTelefone = By.cssSelector("#step-1-telefone");
    private static final By msgErroTelefone = By.cssSelector("#step-1-telefone-helper-text");
    private static final By campoData = By.cssSelector("#step-1-dataNascimento");
    private static final By msgErroData = By.cssSelector("#step-1-dataNascimento-helper-text");
    private static final By campoCidade = By.cssSelector("#step-1-cidade");
    private static final By msgErroCidade = By.cssSelector("#step-1-cidade-helper-text");
    private static final By selectEstado = By.cssSelector("#step-1-estado");
    private static final By selectNeurodiversidade = By.cssSelector("#step-1-neurodiversidade");
    private static final By btnProximo = By.cssSelector("#step-1-enviar");

    @Step("Validar url atual")
    public String validarUrlAtual() {
        return getCurrentUrl();
    }

}
