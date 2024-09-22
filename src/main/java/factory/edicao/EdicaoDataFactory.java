package factory.edicao;

import client.edicao.EdicaoClient;
import models.edicao.EdicaoModel;
import net.datafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EdicaoDataFactory {

    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static final EdicaoClient edicaoClient = new EdicaoClient();


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

	public static EdicaoModel notaDeCorteAcimaDeCem() {

        EdicaoModel edicao = new EdicaoModel();
		edicao.setNotaCorte(faker.random().nextInt(101, 100000));

        return edicao;
    }

	public static EdicaoModel notaDeCorteNegativa() {

        EdicaoModel edicao = new EdicaoModel();
		edicao.setNotaCorte(faker.random().nextInt(-100000, 1));

        return edicao;
    }

    public static EdicaoModel EdicaoCadastrada() {
        EdicaoModel edicao = novaEdicao();

        List<Map<String, Object>> response =
                edicaoClient.listarTodasAsEdicoes()
                        .then()
                        .extract()
                        .as(List.class);

        Map<String, Object> firstEdition = response.get(0);
        Double notaCorteDouble = (Double) firstEdition.get("notaCorte");

        edicao.setIdEdicao((Integer) firstEdition.get("idEdicao"));
        edicao.setNome((String) firstEdition.get("nome"));
        edicao.setNotaCorte(notaCorteDouble.intValue());
        return edicao;
    }
}
