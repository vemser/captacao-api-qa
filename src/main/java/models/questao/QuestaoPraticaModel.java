package models.questao;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QuestaoPraticaModel extends Questao {
    private List<ExemplosModel> exemplos;
}
