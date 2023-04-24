package models.avaliacao;

import lombok.*;
import models.gestor.GestorModel;
import models.inscricao.InscricaoModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AvaliacaoModel {

    private Integer idAvaliacao;
    private GestorModel avaliador;
    private String aprovado;
    private InscricaoModel inscricao;
}