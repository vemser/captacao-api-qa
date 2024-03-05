package utils.config;

import models.trilha.TrilhaModel;

import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static String removerCaracteresEspeciais(String string) {
        return string.replaceAll("[^a-zA-Z0-9 ]", "");
    }

    public static List<String> listaTrilhaParaListaString(List<TrilhaModel> listaDeTrilha) {

        List<String> listaDeTrilhaString = new ArrayList<>();

        listaDeTrilha.forEach(trilha -> listaDeTrilhaString.add(trilha.getNome()));

        return listaDeTrilhaString;
    }
}
