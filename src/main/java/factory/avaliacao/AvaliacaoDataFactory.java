package factory.avaliacao;

import models.avaliacao.AvaliacaoCriacaoModel;

public class AvaliacaoDataFactory {

    public static AvaliacaoCriacaoModel avaliacaoValida(Integer idInscricao, Boolean aprovado) {
        return novaAvaliacao(idInscricao, aprovado);
    }

    public static AvaliacaoCriacaoModel avaliacaoAtualizada(AvaliacaoCriacaoModel avaliacao, Boolean aprovado) {

        avaliacao.setAprovadoBoolean(aprovado);

        return avaliacao;
    }

    private static AvaliacaoCriacaoModel novaAvaliacao(Integer idInscricao, Boolean aprovado) {

        AvaliacaoCriacaoModel avaliacao = new AvaliacaoCriacaoModel();
        avaliacao.setIdInscricao(idInscricao);
        avaliacao.setAprovadoBoolean(aprovado);

        return avaliacao;
    }
}
