package factory.trilha;

import models.trilha.TrilhaModel;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TrilhaDataFactory {
    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static final Random random = new Random();

    public static TrilhaModel trilhaValida() {
        return novaTrilha();
    }

    private static TrilhaModel novaTrilha() {

        TrilhaModel novaTrilha = new TrilhaModel();
        novaTrilha.setNome(faker.lorem().word());

        return novaTrilha;
    }

    private static TrilhaModel novaTrilhaApenasNome() {

        TrilhaModel trilha = new TrilhaModel();
        trilha.setNome(faker.lorem().word());

        return trilha;
    }

    private static List<TrilhaModel> novaListaDeTrilhas(Integer numDeTrilhas) {

        List<TrilhaModel> listaDeTrilhas = new ArrayList<>();

        for (int i = 0; i < numDeTrilhas; i++) {
            listaDeTrilhas.add(novaTrilha());
        }

        return listaDeTrilhas;
    }

    private static List<String> novaListaDeTrilhasApenasNome(Integer numDeTrilhas) {

        List<String> listaDeTrilhas = new ArrayList<>();

        for (int i = 0; i < numDeTrilhas; i++) {
            listaDeTrilhas.add(novaTrilhaApenasNome().getNome().toUpperCase());
        }

        return listaDeTrilhas;
    }
}
