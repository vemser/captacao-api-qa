package models.edicao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdicaoModel {

	private Integer idEdicao;
	private String nome;
	private Integer notaCorte;
}
