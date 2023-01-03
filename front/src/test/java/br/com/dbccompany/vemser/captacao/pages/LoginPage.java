package br.com.dbccompany.vemser.captacao.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends BasePage {

    private static final By campoEmail = By.cssSelector("#login-username");
    private static final By msgcampoEmail = By.cssSelector("");
    private static final By campoSenha = By.cssSelector("#login-password");
    private static final By msgcampoSenha = By.cssSelector("");
    private static final By btnLogin = By.cssSelector("#root > div.MuiBox-root.css-1pf433k > main > div > div.MuiBox-root.css-1v9gjd5 > form > button");

/*    @Step("Validar mensagem erro campo não matriculado")
    public String validarMsgErroNaoMatriculado(){
        return getText(msgErroNaoMatriculado);
    }*/

    @Step("Preencher campo email")
    public void preencherEmail(String email) {
        sendKeys(campoEmail, email);
    }

    @Step("Preencher campo senha")
    public void preencherSenha(String senha) {
        sendKeys(campoSenha, senha);
    }

    @Step("Clicar no botão Login")
    public void clicarBotaoLogin() {
        click(btnLogin);
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
