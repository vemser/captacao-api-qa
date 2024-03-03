package factory.questao;

import models.questao.AlternativasModel;
import models.questao.ExemplosModel;
import models.questao.QuestaoObjetivaModel;
import models.questao.QuestaoPraticaModel;
import net.datafaker.Faker;

import java.util.*;

public class QuestaoPraticaDataFactory {
    private static Faker faker = new Faker(new Locale("pt-BR"));

    public static QuestaoPraticaModel questaoPraticaValida(){
        return novaQuestaoPratica();
    }

    public static QuestaoPraticaModel questaoSemTitulo(){
        return novaQuestaoPraticaSemTitulo();
    }

    public static QuestaoPraticaModel questaoExcedendoOs100CaracteresNoTitulo(){
        return novaQuestaoPraticaComTituloExcedendo100Caracteres();
    }

    public static QuestaoPraticaModel questaoComPraticaComEnunciadoVazio(){
        return novaQuestaoPraticaComEnunciadoVazio();
    }

    public static QuestaoPraticaModel questaoComPraticaExcedendoOs4000CaracteresNoEnunciado(){
        return novaQuestaoPraticaComEnunciadoExcedendo4000Caracteres();
    }

    public static QuestaoPraticaModel questaoComPraticaComDificuldadeInexistente(){
        return novaQuestaoPraticaComDificuldadeInexistente();
    }

    public static QuestaoPraticaModel questaoComPraticaComExemplosVazios(){
        return novaQuestaoPraticaComExemplosVazios();
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

    private static QuestaoPraticaModel novaQuestaoPraticaSemTitulo(){
        QuestaoPraticaModel questaoPraticaModel = new QuestaoPraticaModel();

        questaoPraticaModel.setTitulo("");
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

    private static QuestaoPraticaModel novaQuestaoPraticaComTituloExcedendo100Caracteres() {
        QuestaoPraticaModel questaoPraticaModel = new QuestaoPraticaModel();

        questaoPraticaModel.setTitulo(faker.lorem().characters(101));
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

    private static QuestaoPraticaModel novaQuestaoPraticaComEnunciadoVazio() {
        QuestaoPraticaModel questaoPraticaModel = new QuestaoPraticaModel();

        questaoPraticaModel.setTitulo(faker.book().title());
        questaoPraticaModel.setDificuldade("FACIL");
        questaoPraticaModel.setEnunciado("");
        questaoPraticaModel.setTipo("PRATICA");

        ExemplosModel exemplosModel = new ExemplosModel();
        exemplosModel.setEntradaExplicacao(faker.lorem().sentence());
        exemplosModel.setSaidaExplicacao(faker.lorem().sentence());
        exemplosModel.setExemploEntrada(faker.lorem().sentence());
        exemplosModel.setExemploSaida(faker.lorem().sentence());

        questaoPraticaModel.setExemplos(Collections.singletonList(exemplosModel));

        return questaoPraticaModel;
    }

    private static QuestaoPraticaModel novaQuestaoPraticaComEnunciadoExcedendo4000Caracteres() {
        QuestaoPraticaModel questaoPraticaModel = new QuestaoPraticaModel();

        questaoPraticaModel.setTitulo(faker.book().title());
        questaoPraticaModel.setDificuldade("FACIL");
        questaoPraticaModel.setEnunciado(faker.lorem().characters(4001));
        questaoPraticaModel.setTipo("PRATICA");

        ExemplosModel exemplosModel = new ExemplosModel();
        exemplosModel.setEntradaExplicacao(faker.lorem().sentence());
        exemplosModel.setSaidaExplicacao(faker.lorem().sentence());
        exemplosModel.setExemploEntrada(faker.lorem().sentence());
        exemplosModel.setExemploSaida(faker.lorem().sentence());

        questaoPraticaModel.setExemplos(Collections.singletonList(exemplosModel));
        return questaoPraticaModel;
    }

    private static QuestaoPraticaModel novaQuestaoPraticaComDificuldadeInexistente() {
        QuestaoPraticaModel questaoPraticaModel = new QuestaoPraticaModel();

        questaoPraticaModel.setTitulo(faker.book().title());
        questaoPraticaModel.setDificuldade("Mailton Megazord da Qualidade");
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

    private static QuestaoPraticaModel novaQuestaoPraticaComExemplosVazios() {
        QuestaoPraticaModel questaoPraticaModel = new QuestaoPraticaModel();

        questaoPraticaModel.setTitulo(faker.book().title());
        questaoPraticaModel.setDificuldade("FACIL");
        questaoPraticaModel.setEnunciado(faker.lorem().sentence());
        questaoPraticaModel.setTipo("PRATICA");


        return questaoPraticaModel;
    }
}