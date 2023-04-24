package dataFactory;

import models.trilha.TrilhaApenasNomeModel;
import models.trilha.TrilhaModel;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TrilhaDataFactory {

    private static Faker faker = new Faker(new Locale("pt-BR"));
    private static Random random = new Random();

    public static TrilhaModel trilhaValida() {
        return novaTrilha();
    }

    public static TrilhaApenasNomeModel trilhaValidaApenasNome() {

        return novaTrilhaApenasNome();
    }

    public static List<String> listaDeTrilhasValidasApenasNome(Integer numDeTrilhas) {
        return novaListaDeTrilhasApenasNome(numDeTrilhas);
    }

    public static TrilhaApenasNomeModel trilhaValidaApenasNomePassandoNome(String nomeTrilha) {

        TrilhaApenasNomeModel trilha = novaTrilhaApenasNome();
        trilha.setNome(nomeTrilha);

        return trilha;
    }

    private static TrilhaModel novaTrilha() {

        TrilhaModel trilha = new TrilhaModel();
        trilha.setIdTrilha(random.nextInt());
        trilha.setNome(faker.lorem().word());

        return trilha;
    }

    private static TrilhaApenasNomeModel novaTrilhaApenasNome() {

        TrilhaApenasNomeModel trilha = new TrilhaApenasNomeModel();
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
