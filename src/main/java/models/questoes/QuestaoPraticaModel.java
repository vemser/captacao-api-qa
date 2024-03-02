package models.questoes;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuestaoPraticaModel {
    private String titulo;
    private String dificuldade;
    private String enunciado;
    private String tipo;
    private List<ExemplosModel> exemplos;
}
