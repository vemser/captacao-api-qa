package factory;

import models.prova.ProvaCriacaoModel;

import java.time.LocalDateTime;
import java.util.Random;

public class ProvaDataFactory {

    private static Random random = new Random();
    public static ProvaCriacaoModel provaValida() {
        return novaProva();
    }

    public static ProvaCriacaoModel provaComNovaData(ProvaCriacaoModel provaOriginal) {

        ProvaCriacaoModel provaAtualizada = provaOriginal;
        provaAtualizada.setDataInicio(provaAtualizada.getDataInicio().plusDays(10));
        provaAtualizada.setDataFinal(provaAtualizada.getDataFinal().plusDays(15));

        return provaAtualizada;
    }

    private static ProvaCriacaoModel novaProva() {

        Integer horaAleatoriaInicio = random.nextInt(18);
        Integer minutoAleatorioInicio = random.nextInt(60);
        Integer segundoAleatorioInicio = random.nextInt(60);

        Integer diasNoFuturoInicio = random.nextInt(1000);

        Integer diaFuturoInicio = LocalDateTime.now().plusDays(diasNoFuturoInicio).getDayOfMonth();
        Integer mesFuturoInicio = LocalDateTime.now().plusDays(diasNoFuturoInicio).getMonthValue();
        Integer anoFuturoInicio = LocalDateTime.now().plusDays(diasNoFuturoInicio).getYear();

        LocalDateTime dataInicio = LocalDateTime.of(anoFuturoInicio, mesFuturoInicio, diaFuturoInicio, 15, 47, 2, 602000000);

        Integer horaAleatoriaFinal = random.nextInt(18);
        Integer minutoAleatorioFinal = random.nextInt(60);
        Integer segundoAleatorioFinal = random.nextInt(60);

        Integer diasNoFuturoFinal = random.nextInt(1000);

        Integer diaFuturoFinal = LocalDateTime.now().plusDays(diasNoFuturoFinal).getDayOfMonth();
        Integer mesFuturoFinal = LocalDateTime.now().plusDays(diasNoFuturoFinal).getMonthValue();
        Integer anoFuturoFinal = LocalDateTime.now().plusDays(diasNoFuturoFinal).getYear();

        LocalDateTime dataFinal = LocalDateTime.of(anoFuturoFinal, mesFuturoFinal, diaFuturoFinal, 15, 47, 2, 602000000);


        ProvaCriacaoModel prova = new ProvaCriacaoModel();
        prova.setDataInicio(dataInicio);
        prova.setDataFinal(dataFinal);

        return prova;
    }
}
