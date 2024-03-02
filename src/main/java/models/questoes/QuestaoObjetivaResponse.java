package models.questoes;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuestaoObjetivaResponse extends QuestaoObjetivaModel{
    private String message;
    private Integer idCandidatoProva;
}
