package factory.entrevista;

import models.entrevista.EntrevistaCriacaoModel;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class EntrevistaDataFactory {

    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static final Random random = new Random();

    public static EntrevistaCriacaoModel entrevistaCriacaoValida(String emailDoCandidato, Boolean avaliado) {
        return novaEntrevistaCriacao(emailDoCandidato, avaliado);
    }

    private static EntrevistaCriacaoModel novaEntrevistaCriacao(String emailDoCandidato, Boolean avaliado) {

        String candidatoAvaliado = avaliado ? "T" : "F";

        int randomDay = random.nextInt(28);
        int randomMonth = random.nextInt(12);
        int randomYear = random.nextInt(2025, 2040);


        String randomDayStr = randomDay < 10 ? "0" + randomDay : String.valueOf(randomDay);
        String randomMonthStr = randomMonth < 10 ? "0" + randomMonth : String.valueOf(randomMonth);
        String randomYearStr = randomYear < 10 ? "0" + randomYear : String.valueOf(randomYear);

        EntrevistaCriacaoModel entrevista = new EntrevistaCriacaoModel();
        entrevista.setCandidatoEmail(emailDoCandidato);
        entrevista.setDataEntrevista(randomYearStr + "-" + randomMonthStr + "-" + randomDayStr + "T09:48:30.519Z");
        entrevista.setObservacoes(faker.lorem().sentence(4));
        entrevista.setAvaliado(candidatoAvaliado);

        return entrevista;
    }
}
