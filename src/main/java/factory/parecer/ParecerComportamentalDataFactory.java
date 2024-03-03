package factory.parecer;

import models.parecerComportamental.ParecerComportamentalModel;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class ParecerComportamentalDataFactory {

    private static Random random = new Random();
    private static Faker faker = new Faker(new Locale("pt-BR"));

    public static ParecerComportamentalModel parecerComportamentalValido() {
        return novoParecerComportamental();
    }

    private static ParecerComportamentalModel novoParecerComportamental() {

        ParecerComportamentalModel parecerComportamental = new ParecerComportamentalModel();
        parecerComportamental.setNotaComportamental(random.nextDouble(100));
        parecerComportamental.setParecerComportamental(faker.lorem().sentence(3));

        return parecerComportamental;
    }
}
