package br.com.dbccompany.vemser.captacao.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private static final By btnInscricao =
            By.cssSelector("#root > div.MuiBox-root.css-li1vzv > div > div > div > div.MuiBox-root.css-exwvu8 > div > button");

    private static final By btnEntrar = By.cssSelector("#button-admin");

    @Step("Clicar no botão Inscrição")
    public void clicarBotaoInscricao() {
        click(btnInscricao);
    }

    @Step("Clicar no botão Entrar Como Administrador")
    public void clicarBotaoEntrar() {
        click(btnEntrar);
    }

    @Step("Validar url atual")
    public String validarUrlAtual() {
        return getCurrentUrl();
    }

}
