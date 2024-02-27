package factory;

import models.edicao.EdicaoModel;
import client.EdicaoClient;

public class EdicaoDataFactory {

    private static EdicaoClient edicaoClient = new EdicaoClient();

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
