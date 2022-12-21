package br.com.dbccompany.vemser.captacao.pages;

import br.com.dbccompany.vemser.captacao.utils.Elements;
import org.openqa.selenium.By;

public class BasePage extends Elements {

    public static void click(By by) {
        waitElement(by);
        element(by).click();
    }

    public static void clear(By by){
        waitElement(by);
        element(by).clear();
    }

    public static void sendKeys(By by, String texto){
        waitElement(by);
        element(by).sendKeys(texto);
    }

    public static boolean isSelected(By by) {
        waitElement(by);
        return element(by).isSelected();
    }

    public static String getText(By by){
        waitElement(by);
        return element(by).getText();
    }

    public static String getAttributeInnerText(By by){
        waitElement(by);
        return element(by).getAttribute("innerText");
    }

    public static String getPageSource(){
        return driver.getPageSource();
    }

    public static String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
