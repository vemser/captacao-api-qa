package factory;

import models.parecerTecnico.ParecerTecnicoModel;
import net.datafaker.Faker;

import java.util.Random;

public class ParecerTecnicoDataFactory {

    private static Random random = new Random();
    private static Faker faker = new Faker();

    public static ParecerTecnicoModel parecerTecnicoValido() {
        return novoParecerTecnico();
    }

    private static ParecerTecnicoModel novoParecerTecnico() {

        ParecerTecnicoModel parecerTecnico = new ParecerTecnicoModel();
        parecerTecnico.setNotaTecnica(random.nextDouble(100));
        parecerTecnico.setParecerTecnico(faker.lorem().sentence(3));

        return parecerTecnico;
    }
}
