package br.com.dbccompany.vemser.captacao.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private static final By btnInscricao =
            By.cssSelector("#root > div.MuiBox-root.css-li1vzv > div > div > div > div.MuiBox-root.css-exwvu8 > div > button");

    @Step("Clicar no botão Inscrição")
    public void clicarBotaoInscricao() {
        click(btnInscricao);
    }

    @Step("Validar url atual")
    public String validarUrlAtual() {
        return getCurrentUrl();
    }

}
