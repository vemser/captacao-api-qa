package dataFactory;

import models.linguagem.LinguagemModel;
import net.datafaker.Faker;

import java.util.Locale;

public class LinguagemDataFactory {

    private static Faker faker = new Faker(new Locale("pt-BR"));


    public LinguagemModel linguagemValida() {
        return novaLinguagem();
    }

    private LinguagemModel novaLinguagem() {

        LinguagemModel linguagem = new LinguagemModel();
        linguagem.setNome(faker.lorem().sentence());

        return linguagem;
    }
}
