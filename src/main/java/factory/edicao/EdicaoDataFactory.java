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
        edicao.setIdEdicao(faker.random().nextInt(100, 100000));
        edicao.setNome("VEMSER_"+faker.random().nextInt(100, 100000));

    		edicao.setNotaCorte(faker.random().nextInt(0, 100));

        return edicao;
    }

	public static EdicaoModel notaDeCorte() {

        EdicaoModel edicao = new EdicaoModel();
		edicao.setNotaCorte(faker.random().nextInt(0, 100));

        return edicao;
    }

}
