package models.prova;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PreProvaUsuarioModel {
    private int idPreProva;
    private int idCandidato;
    private int notaPreProva;
    private String observacao;
    private String acao;
}
