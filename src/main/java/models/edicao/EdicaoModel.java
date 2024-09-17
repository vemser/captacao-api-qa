package models.edicao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EdicaoModel {

    private Integer idEdicao;
    private String nome;
	@JsonProperty("notaCorte")
	private Integer notaCorte;
}
