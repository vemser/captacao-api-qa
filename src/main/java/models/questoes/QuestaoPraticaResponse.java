package models.questoes;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuestaoPraticaResponse extends QuestaoPraticaModel{
    private String message;
    private Integer idCandidatoProva;
}
