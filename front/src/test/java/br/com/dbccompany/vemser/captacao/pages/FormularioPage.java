package br.com.dbccompany.vemser.captacao.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FormularioPage extends BasePage {

    private static final By btnMatriculadoSim = By.cssSelector("#matriculado-sim");
    private static final By btnMatriculadoNao = By.cssSelector("#matriculado-nao");
    private static final By msgErroNaoMatriculado = By.cssSelector("#erro-não-matriculado");
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
    private static final By selectLinguagensProgramacao = By.cssSelector("#s2-select-linguagens-checkbox");
    private static final By checkboxJava = By.cssSelector("#menu- > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-" +
            "rounded.MuiPaper-elevation1.MuiPaper-root.MuiMenu-paper.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-" +
            "elevation8.MuiPopover-paper.css-177ic5c > ul > li:nth-child(3) > span.MuiButtonBase-root.MuiCheckbox-" +
            "root.MuiCheckbox-colorPrimary.PrivateSwitchBase-root.MuiCheckbox-root.MuiCheckbox-colorPrimary." +
            "MuiCheckbox-root.MuiCheckbox-colorPrimary.css-1ujiglk > input");
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
    private static final By btnAdicionarCurriculo = By.cssSelector("#botao-curriculo");
    private static final By btnAdicionarPrintConficugaroes = By.cssSelector("#botao-configuracoes");
    private static final By btnVoltar = By.cssSelector("#s2-botao-voltar");
    private static final By btnEnviar = By.cssSelector("#s2-botao-submit");


    @Step("Clicar em não matriculado")
    public void clicarNaoMatriculado() {
        click(btnMatriculadoNao);
    }

    @Step("Validar mensagem erro campo não matriculado")
    public String validarMsgErroNaoMatriculado(){
        return getText(msgErroNaoMatriculado);
    }

    @Step("Selecionar opção de turno de estudo")
    public void selectTurno() {
        click(btnTurnoNoite);
    }

    @Step("Preencher campo instituição de ensino")
    public void preencherInstituicaoEnsino() {
        sendKeys(campoInstituicaoDeEnsino, "Instrituição Teste");
    }

    @Step("Preencher campo curso")
    public void preencherCurso() {
        sendKeys(campoCurso, "Curso Teste");
    }

    @Step("Selecionar nível de inglês")
    public void selectIngles() {
        click(selectNivelIngles);
        Select select = new Select((WebElement) selectNivelIngles);
        select.selectByVisibleText("Fluente");
    }

    @Step("Selecionar nível de espanhol")
    public void selectEspanhol() {
        Select select = new Select((WebElement) selectNivelEspanhol);
        select.selectByVisibleText("Intermediário");
    }

    @Step("Selecionar orientação sexual")
    public void selectOrientacaoSexual() {
        Select select = new Select((WebElement) selectOrientacaoSexual);
        select.selectByVisibleText("Prefiro não informar");
    }

    @Step("Selecionar gênero")
    public void selectGenero() {
        Select select = new Select((WebElement) selectGenero);
        select.selectByVisibleText("Prefiro não informar");
    }

    @Step("Selecionar linguagens de programação")
    public void selectLinguagensProgramacao() {
        click(selectLinguagensProgramacao);
/*        Select select = new Select((WebElement) selectLinguagensProgramacao);
        select.selectByVisibleText("Java");*/
        click(checkboxJava);
    }

    @Step("Selecionar trilha")
    public void selectTrilha() {
        click(btnTrilhaQA);
    }

    @Step("Selecionar deficiência")
    public void selectDeficiencia() {
        click(selectDeficiencia);
        Select select = new Select((WebElement) selectDeficiencia);
        select.selectByVisibleText("Sim");
    }

    @Step("Preencher campo deficiência")
    public void preencherDeficiencia() {
        sendKeys(campoDeficiencia, "Deficiência teste");
    }

    @Step("Selecionar motivação")
    public void selectMotivo() {
        click(selectMotivacaoDesafios);
    }

    @Step("Selecionar motivação outro")
    public void selectMotivoOutro() {
        click(selectMotivacaoOutro);
    }

    @Step("Preencher campo motivação outro")
    public void preencherMotivo() {
        sendKeys(campoMotivacaoOutro, "Motivo teste");
    }

    @Step("Preencher campo algo importante")
    public void preencherAlgoImportante() {
        sendKeys(campoAlgoImportante, "Algo importante teste");
    }

    @Step("Selecionar conhecimento para prova")
    public void selectProva() {
        click(btnConhecimentoSim);
    }

    @Step("Selecionar disponibilidade de efetivação")
    public void selectDisponibilidadeEfetivacao() {
        click(btnDisponibilidadeAnoSim);
    }

    @Step("Selecionar disponibilidade")
    public void selectDisponibilidade() {
        click(btnDisponibilidadeTurnoSim);
    }

    @Step("Clicar no botão Enviar")
    public void clicarBotaoEnviar() {
        click(btnEnviar);
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
