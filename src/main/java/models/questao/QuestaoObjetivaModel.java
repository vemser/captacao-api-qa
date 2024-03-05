package models.questao;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QuestaoObjetivaModel extends Questao {
    private Integer respostaObjetiva;
    private boolean ativo;
    private List<AlternativasModel> alternativasObjetivas;
}
