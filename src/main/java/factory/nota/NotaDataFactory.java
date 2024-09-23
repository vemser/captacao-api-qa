package factory.nota;

import models.nota.NotaModel;

public class NotaDataFactory {

    private NotaModel novaNota(Double valorNota) {

        NotaModel nota = new NotaModel();
        nota.setNotaProva(valorNota);

        return nota;
    }
}
