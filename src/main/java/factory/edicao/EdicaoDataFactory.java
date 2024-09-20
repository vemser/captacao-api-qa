package factory.edicao;

import client.edicao.EdicaoClient;
import models.edicao.EdicaoModel;
import models.edicao.EdicaoResponse;
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

    public static EdicaoModel idNovaEdicao() {
        return criandoIdNovaEdicao();
    }

    private static EdicaoModel novaEdicao() {

        EdicaoModel edicao = new EdicaoModel();
        edicao.setIdEdicao(faker.random().nextInt(100, 100000));
        edicao.setNome("VEMSER_"+faker.random().nextInt(100, 100000));
        edicao.setNotaCorte(faker.random().nextInt(0, 100));

        return edicao;
    }

    private static EdicaoModel criandoIdNovaEdicao(){
        EdicaoModel edicao = new EdicaoModel();
        edicao.setIdEdicao(faker.random().nextInt(100, 100000));

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
