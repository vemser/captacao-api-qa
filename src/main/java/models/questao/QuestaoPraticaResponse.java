package models.questao;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuestaoPraticaResponse extends QuestaoPraticaModel{
    private String mensagem;
    private Integer idCandidatoProva;
}
