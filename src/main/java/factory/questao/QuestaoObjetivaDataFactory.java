package factory.questao;

import models.questao.AlternativasModel;
import models.questao.QuestaoObjetivaModel;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestaoObjetivaDataFactory {
    private static final Faker faker = new Faker(new Locale("pt-BR"));
    public static final String DIFICULDADE_FACIL = "FACIL";

    public static QuestaoObjetivaModel questaoObjetivaValida() {
        return criarQuestaoObjetiva(faker.book().title(), faker.lorem().sentence(), DIFICULDADE_FACIL, criarAlternativasPadrao());
    }

    public static QuestaoObjetivaModel questaoObjetivaComDuasAlternativas() {
        return criarQuestaoObjetiva(faker.book().title(), faker.lorem().sentence(), DIFICULDADE_FACIL, criarDuasAlternativas());
    }

    public static QuestaoObjetivaModel questaoSemTitulo() {
        return criarQuestaoObjetiva("", faker.lorem().sentence(), DIFICULDADE_FACIL, criarAlternativasPadrao());
    }

    public static QuestaoObjetivaModel questaoExcedendoOs100CaracteresNoTitulo() {
        return criarQuestaoObjetiva(faker.lorem().characters(101), faker.lorem().sentence(), DIFICULDADE_FACIL, criarAlternativasPadrao());
    }

    public static QuestaoObjetivaModel questaoObjetivaComEnunciadoVazio() {
        return criarQuestaoObjetiva(faker.book().title(), "", DIFICULDADE_FACIL, criarAlternativasPadrao());
    }

    public static QuestaoObjetivaModel questaoObjetivaExcedendoOs4000CaracteresNoEnunciado() {
        return criarQuestaoObjetiva(faker.book().title(), faker.lorem().characters(4001), DIFICULDADE_FACIL, criarAlternativasPadrao());
    }

    public static QuestaoObjetivaModel questaoObjetivaComDificuldadeInexistente() {
        return criarQuestaoObjetiva(faker.book().title(), faker.lorem().sentence(), "Ályson da qualidade", criarAlternativasPadrao());
    }

    public static QuestaoObjetivaModel questaoObjetivaComApenasUmaAlternativa() {
        List<AlternativasModel> alternativas = new ArrayList<>();
        alternativas.add(new AlternativasModel("Alternativa 1", true));
        return criarQuestaoObjetiva(faker.book().title(), faker.lorem().sentence(), DIFICULDADE_FACIL, alternativas);
    }

    public static QuestaoObjetivaModel questaoObjetivaComNenhumaAlternativaCorreta() {
        List<AlternativasModel> alternativas = criarAlternativasPadrao();
        // Desmarcar a primeira alternativa como correta
        alternativas.get(0).setCorreta(false);
        return criarQuestaoObjetiva(faker.book().title(), faker.lorem().sentence(), DIFICULDADE_FACIL, alternativas);
    }

    public static QuestaoObjetivaModel questaoObjetivaComMaisDeCincoAlternativas() {
        List<AlternativasModel> alternativas = criarAlternativasPadrao();
        // Adicionar mais uma alternativa
        alternativas.add(new AlternativasModel("Alternativa 6", false));
        return criarQuestaoObjetiva(faker.book().title(), faker.lorem().sentence(), DIFICULDADE_FACIL, alternativas);
    }

    public static List<AlternativasModel> criarCincoAlternativas() {
        return criarAlternativasPadrao();
    }

    public static List<AlternativasModel> criarCincoAlternativasFalsas() {
        List<AlternativasModel> alternativas = criarAlternativasPadrao();
        alternativas.get(0).setCorreta(false);
        return alternativas;
    }

    private static List<AlternativasModel> criarAlternativasPadrao() {
        List<AlternativasModel> alternativas = new ArrayList<>();
        alternativas.add(new AlternativasModel("Alternativa 1", true));
        for (int i = 2; i <= 5; i++) {
            alternativas.add(new AlternativasModel("Alternativa " + i, false));
        }
        return alternativas;
    }

    private static List<AlternativasModel> criarDuasAlternativas() {
        List<AlternativasModel> alternativas = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            alternativas.add(new AlternativasModel("Alternativa " + i, i == 1));
        }
        return alternativas;
    }

    private static QuestaoObjetivaModel criarQuestaoObjetiva(String titulo, String enunciado, String dificuldade, List<AlternativasModel> alternativas) {
        QuestaoObjetivaModel questaoObjetivaModel = new QuestaoObjetivaModel();
        questaoObjetivaModel.setRespostaObjetiva(1);
        questaoObjetivaModel.setTitulo(titulo);
        questaoObjetivaModel.setDificuldade(dificuldade);
        questaoObjetivaModel.setEnunciado(enunciado);
        questaoObjetivaModel.setAtivo(true);
        questaoObjetivaModel.setTipo("OBJETIVA");
        questaoObjetivaModel.setAlternativasObjetivas(alternativas);
        return questaoObjetivaModel;
    }
}