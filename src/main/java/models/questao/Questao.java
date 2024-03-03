package models.questao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class Questao {
    private String titulo;
    private String dificuldade;
    private String enunciado;
    private String tipo;
}
