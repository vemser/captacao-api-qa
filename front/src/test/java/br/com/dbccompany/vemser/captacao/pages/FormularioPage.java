package br.com.dbccompany.vemser.captacao.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FormularioPage extends BasePage {

    private static final By btnMatriculadoSim = By.cssSelector("#matriculado-sim");
    private static final By btnMatriculadoNao = By.cssSelector("#matriculado-nao");
    private static final By btnTurnoManha = By.cssSelector("#turno-manha");
    private static final By btnTurnoTarde = By.cssSelector("#turno-tarde");
    private static final By btnTurnoNoite = By.cssSelector("#turno-noite");
    private static final By campoInstituicaoDeEnsino = By.cssSelector("#instituicao-de-ensino-candidato");
    private static final By msgcampoInstituicaoDeEnsino = By.cssSelector("#instituicao-de-ensino-candidato-helper-text");
    private static final By campoCurso = By.cssSelector("#curso-candidato");
    private static final By msgcampoCurso = By.cssSelector("#curso-candidato-helper-text");
    private static final By selectNivelIngles = By.cssSelector("#nivel-ingles-candidato");
    private static final By selectNivelEspanhol = By.cssSelector("#nivel-espanhol-candidato");
    private static final By selectOrientacaoSexual = By.cssSelector("#orientacao-sexual-candidato");
    private static final By selectGenero = By.cssSelector("#mui-component-select-genero");
    private static final By btnTrilhaBackend = By.cssSelector("#s2-trilha-backend");
    private static final By btnTrilhaFrontend = By.cssSelector("#s2-trilha-frontend");
    private static final By btnTrilhaQA = By.cssSelector("#s2-trilha-qa");
    private static final By selectDeficiencia = By.cssSelector("#s2-select-deficiencia-candidato");
    private static final By campoDeficiencia = By.cssSelector("#s2-candidato-deficiencia-descricao");
    private static final By selectMotivacaoDesafios = By.cssSelector("#s2-candidato-desafio");
    private static final By selectMotivacaoOutro = By.cssSelector("#s2-candidato-outro");
    private static final By campoMotivacaoOutro = By.cssSelector("#s2-candidato-motivo");
    private static final By msgcampoMotivacao = By.cssSelector("mensagem-erro-outro-motivo");
    private static final By campoAlgoImportante = By.cssSelector("#textarea-importante-candidato");
    private static final By msgcampoAlgoImportante = By.cssSelector("#textarea-importante-candidato-helper-text");
    private static final By btnConhecimentoSim = By.cssSelector("#s2-candidato-prova-sim");
    private static final By btnConhecimentoNao = By.cssSelector("#s2-candidato-prova-nao");
    private static final By btnDisponibilidadeAnoSim = By.cssSelector("#s2-candidato-efetivacao-sim");
    private static final By btnDisponibilidadeAnoNao = By.cssSelector("#s2-candidato-efetivacao-nao");
    private static final By btnDisponibilidadeTurnoSim = By.cssSelector("#s2-candidato-disponibilidade-sim");
    private static final By btnDisponibilidadeTurnoNao = By.cssSelector("#s2-candidato-disponibilidade-nao");
    private static final By campoGitHub = By.cssSelector("#s2-candidato-github");
    private static final By campoLinkedin = By.cssSelector("#s2-candidato-linkedin");
    private static final By btnAdicionarCurriculo = By.cssSelector("#root > div.MuiBox-root.css-uqekie > " +
            "div > div > div > div.MuiGrid-root.MuiGrid-item.MuiGrid-grid-xs-12.css-1au9qiw > form > " +
            "div:nth-child(19) > div > div > label");
    private static final By btnAdicionarPrintConficugaroes = By.cssSelector("#root > div.MuiBox-root.css-uqekie > " +
            "div > div > div > div.MuiGrid-root.MuiGrid-item.MuiGrid-grid-xs-12.css-1au9qiw > form > " +
            "div:nth-child(20) > div > div > label");
    private static final By btnVoltar = By.cssSelector("#s2-botao-voltar");
    private static final By btnEnviar = By.cssSelector("#s2-botao-submit");


    @Step("Selecionar opcao de nao matriculado")
    public void selectNaoMatriculado() {
        click(btnMatriculadoNao);
    }

    @Step("Selecionar turno de estudo do candidato")
    public void selectTurno() {
        click(btnTurnoNoite);
    }

    @Step("Preencher campo institucao de ensino")
    public void preencherInstituicaoEnsino() {
        sendKeys(campoInstituicaoDeEnsino, "TESTE INSTITUICAO");
    }

    @Step("Preencher campo curso")
    public void preencherCurso() {
        sendKeys(campoCurso, "TESTE CURSO");
    }

    @Step("Selecionar nivel de ingles")
    public void selectIngles() {
        Select select = new Select((WebElement) selectNivelIngles);
        select.selectByVisibleText("Fluente");
    }

    @Step("Selecionar nivel de espanhol")
    public void selectEspanhol() {
        Select select = new Select((WebElement) selectNivelEspanhol);
        select.selectByVisibleText("Intermediário");
    }

    @Step("Selecionar orientacao sexual")
    public void selectOrientacaoSexual() {
        Select select = new Select((WebElement) selectOrientacaoSexual);
        select.selectByVisibleText("Prefiro não informar");
    }

    @Step("Selecionar Genero")
    public void selectGenero() {
        Select select = new Select((WebElement) selectGenero);
        select.selectByVisibleText("Prefiro não informar");
    }

    @Step("Selecionar trilha")
    public void selectTrilha() {
        click(btnTrilhaQA);
    }

    @Step("Selecionar Deficiencia")
    public void selectDeficiencia() {
        Select select = new Select((WebElement) selectDeficiencia);
        select.selectByVisibleText("Sim");
    }

    @Step("Preencher campo deficiencia")
    public void preencherDeficiencia() {
        sendKeys(campoDeficiencia, "TESTE DEFICIENCIA");
    }

    @Step("Selecionar motivacao")
    public void selectMotivo() {
        click(selectMotivacaoOutro);
    }

    @Step("Preencher campo motivacao")
    public void preencherMotivo() {
        sendKeys(campoMotivacaoOutro, "TESTE MOTIVO");
    }

    @Step("Selecionar conhecimento para prova")
    public void selectProva() {
        click(btnConhecimentoSim);
    }

    @Step("Selecionar disponibilidade de efetivacao")
    public void selectDisponibilidadeEfetivacao() {
        click(btnDisponibilidadeAnoSim);
    }

    @Step("Selecionar trilha")
    public void selectDisponibilidade() {
        click(btnDisponibilidadeTurnoSim);
    }

    @Step("Validar url atual")
    public String validarUrlAtual() {
        return getCurrentUrl();
    }

}
