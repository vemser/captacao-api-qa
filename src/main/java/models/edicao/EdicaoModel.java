package models.edicao;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EdicaoModel {

    private Integer idEdicao;
    private String nome;
	private Integer notaCorte;
}
