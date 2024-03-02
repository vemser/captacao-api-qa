package models.questao;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuestaoObjetivaModel {
    private Integer respostaObjetiva;
    private String titulo;
    private String dificuldade;
    private String enunciado;
    private boolean ativo;
    private String tipo;
    private List<AlternativasModel> alternativasObjetivas;
}
