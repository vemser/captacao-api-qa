package factory.edicao;

import models.edicao.EdicaoModel;
import net.datafaker.Faker;

import java.util.Locale;

public class EdicaoDataFactory {
    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public static EdicaoModel edicaoValida() {
        return novaEdicao();
    }

    public static EdicaoModel idNovaEdicao() {
        return criandoIdNovaEdicao();
    }

    private static EdicaoModel novaEdicao() {

        EdicaoModel edicao = new EdicaoModel();
        edicao.setIdEdicao(faker.random().nextInt(100, 100000));
        edicao.setNome("VEMSER_"+faker.random().nextInt(100, 100000));

        return edicao;
    }

    private static EdicaoModel criandoIdNovaEdicao(){
        EdicaoModel edicao = new EdicaoModel();
        edicao.setIdEdicao(faker.random().nextInt(100, 100000));
		edicao.setNome(faker.lorem().word());
		edicao.setNotaCorte(faker.random().nextInt(0, 100));

        return edicao;
    }

}
