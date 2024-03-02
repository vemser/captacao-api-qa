package factory.questao;

import models.questao.AlternativasModel;
import models.questao.ExemplosModel;
import models.questao.QuestaoObjetivaModel;
import models.questao.QuestaoPraticaModel;
import net.datafaker.Faker;

import java.util.*;

public class QuestaoDataFactory {
    private static Faker faker = new Faker(new Locale("pt-BR"));

    public static QuestaoObjetivaModel questaoObjetivaValida(){
        return novaQuestaoObjetiva();
    }

    public static QuestaoPraticaModel questaoPraticaValida(){
        return novaQuestaoPratica();
    }

    private static QuestaoObjetivaModel novaQuestaoObjetiva(){
        QuestaoObjetivaModel questaoObjetivaModel = new QuestaoObjetivaModel();

        questaoObjetivaModel.setRespostaObjetiva(1);
        questaoObjetivaModel.setTitulo(faker.book().title());
        questaoObjetivaModel.setDificuldade("FACIL");
        questaoObjetivaModel.setEnunciado(faker.lorem().sentence());
        questaoObjetivaModel.setAtivo(true);
        questaoObjetivaModel.setTipo("OBJETIVA");
        // Criar uma lista de alternativas
        List<AlternativasModel> alternativas = new ArrayList<>();

        // Adicionar até 5 alternativas
        alternativas.add(new AlternativasModel("Alternativa 1", true));
        alternativas.add(new AlternativasModel("Alternativa 2", false));
        alternativas.add(new AlternativasModel("Alternativa 3", false));
        alternativas.add(new AlternativasModel("Alternativa 4", false));
        alternativas.add(new AlternativasModel("Alternativa 5", false));

        // Definir a lista de alternativas no objeto QuestaoObjetivaModel
        questaoObjetivaModel.setAlternativasObjetivas(alternativas);

        return questaoObjetivaModel;
    }

    private static QuestaoPraticaModel novaQuestaoPratica(){
        QuestaoPraticaModel questaoPraticaModel = new QuestaoPraticaModel();

        questaoPraticaModel.setTitulo(faker.book().title());
        questaoPraticaModel.setDificuldade("FACIL");
        questaoPraticaModel.setEnunciado(faker.lorem().sentence());
        questaoPraticaModel.setTipo("PRATICA");

        ExemplosModel exemplosModel = new ExemplosModel();
        exemplosModel.setEntradaExplicacao(faker.lorem().sentence());
        exemplosModel.setSaidaExplicacao(faker.lorem().sentence());
        exemplosModel.setExemploEntrada(faker.lorem().sentence());
        exemplosModel.setExemploSaida(faker.lorem().sentence());

        questaoPraticaModel.setExemplos(Collections.singletonList(exemplosModel));

        return questaoPraticaModel;
    }
}
