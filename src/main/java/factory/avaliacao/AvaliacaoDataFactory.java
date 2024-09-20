package factory.avaliacao;

import models.avaliacao.AvaliacaoCriacaoModel;
import net.datafaker.Faker;

public class AvaliacaoDataFactory {

    private static final Faker faker = new Faker();

    public static AvaliacaoCriacaoModel avaliacaoValida(Integer idInscricao, Boolean aprovado) {
        return novaAvaliacao(idInscricao, aprovado);
    }

    public static AvaliacaoCriacaoModel avaliacaoAtualizada(AvaliacaoCriacaoModel avaliacao, Boolean aprovado, Integer idInscricao) {
        avaliacao.setIdInscricao(idInscricao);
        avaliacao.setAprovadoBoolean(aprovado);

        return avaliacao;
    }

    private static AvaliacaoCriacaoModel novaAvaliacao(Integer idInscricao, Boolean aprovado) {

        AvaliacaoCriacaoModel avaliacao = new AvaliacaoCriacaoModel();
        avaliacao.setIdInscricao(idInscricao);
        avaliacao.setAprovadoBoolean(aprovado);

        return avaliacao;
    }

    public static AvaliacaoCriacaoModel avaliacaoAtualizarIdInscricaoNegativo(AvaliacaoCriacaoModel avaliacao, Boolean aprovado){
        avaliacao.setIdInscricao(faker.random().nextInt(-100, -1));
        return avaliacao;
    }
}
