package dataFactory;

import models.edicao.EdicaoModel;
import net.datafaker.Faker;
import service.EdicaoService;

import java.util.Locale;

public class EdicaoDataFactory {

    private static EdicaoService edicaoService = new EdicaoService();

    public static EdicaoModel edicaoValida() {
        return novaEdicao();
    }

    private static EdicaoModel novaEdicao() {

        EdicaoModel edicao = new EdicaoModel();
        edicao.setIdEdicao(1115);
        edicao.setNome("EDICAO_1115");

        return edicao;
    }
}
