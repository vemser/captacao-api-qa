package factory.edicao;

import models.edicao.EdicaoModel;
import net.datafaker.Faker;

import java.util.Locale;

public class EdicaoDataFactory {
    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static EdicaoModel edicaoValida() {
        return novaEdicao();
    }

    private static EdicaoModel novaEdicao() {

        EdicaoModel edicao = new EdicaoModel();
        edicao.setNome("VEMSER_"+faker.random().nextInt(100));

        return edicao;
    }
}
