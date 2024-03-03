package factory.prova;

import models.edicao.EdicaoModel;
import models.prova.ProvaCriacaoModel;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ProvaDataFactory {

    private static Random random = new Random();
    private static Faker faker = new Faker(new Locale("pt-BR"));

    public static ProvaCriacaoModel provaValida() {
        return novaProva();
    }

    private static ProvaCriacaoModel novaProva() {
                Integer diasNoFuturoInicio = random.nextInt(1000);

        Integer diaFuturoInicio = LocalDateTime.now().plusDays(diasNoFuturoInicio).getDayOfMonth();
        Integer mesFuturoInicio = LocalDateTime.now().plusDays(diasNoFuturoInicio).getMonthValue();
        Integer anoFuturoInicio = LocalDateTime.now().plusDays(diasNoFuturoInicio).getYear();

        LocalDateTime dataInicio = LocalDateTime.of(anoFuturoInicio, mesFuturoInicio, diaFuturoInicio, 15, 47, 2, 602000000);

        Integer diasNoFuturoFinal = random.nextInt(1000);

        Integer diaFuturoFinal = LocalDateTime.now().plusDays(diasNoFuturoFinal).getDayOfMonth();
        Integer mesFuturoFinal = LocalDateTime.now().plusDays(diasNoFuturoFinal).getMonthValue();
        Integer anoFuturoFinal = LocalDateTime.now().plusDays(diasNoFuturoFinal).getYear();

        LocalDateTime dataFinal = LocalDateTime.of(anoFuturoFinal, mesFuturoFinal, diaFuturoFinal, 15, 47, 2, 602000000);


        ProvaCriacaoModel prova = new ProvaCriacaoModel();
        prova.setDataInicio(dataInicio);
        prova.setDataFinal(dataFinal);
        prova.setVersaoProva(faker.random().nextInt());
        prova.setTituloProva(faker.book().title());
        prova.setEnunciadoProva(faker.lorem().sentence());

        EdicaoModel edicaoModel = new EdicaoModel();
        edicaoModel.setIdEdicao(1);

        prova.setIdEdicao(edicaoModel.getIdEdicao());

        List<Integer> linguagens = new ArrayList<>();

        linguagens.add(22);
        linguagens.add(23);
        linguagens.add(24);
        linguagens.add(37);

        prova.setIdsLinguagens(linguagens);

        List<Integer> idQuestoes = new ArrayList<>();
        idQuestoes.add(1);
        idQuestoes.add(2);
        idQuestoes.add(3);
        idQuestoes.add(4);
        idQuestoes.add(5);
        idQuestoes.add(6);
        idQuestoes.add(7);
        idQuestoes.add(8);
        idQuestoes.add(9);
        idQuestoes.add(10);

        prova.setIdQuestoes(idQuestoes);

        return prova;
    }
}
