package factory;

import models.trilha.TrilhaModel;
import net.datafaker.Faker;

import java.util.Locale;

public class TrilhaDataFactory {
    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static TrilhaModel trilhaValida() {
        return novaTrilha();
    }

    private static TrilhaModel novaTrilha() {

        TrilhaModel novaTrilha = new TrilhaModel();
        novaTrilha.setNome(faker.lorem().word());

        return novaTrilha;
    }

}
