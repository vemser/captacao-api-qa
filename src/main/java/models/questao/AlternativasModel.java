package models.questao;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AlternativasModel {
    private String nome;
    private boolean correta;
}
