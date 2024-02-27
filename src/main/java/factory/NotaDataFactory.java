package factory;

import models.nota.NotaModel;

public class NotaDataFactory {

    public NotaModel notaValida(Double valorNota) {
        return novaNota(valorNota);
    }

    private NotaModel novaNota(Double valorNota) {

        NotaModel nota = new NotaModel();
        nota.setNotaProva(valorNota);

        return nota;
    }
}
