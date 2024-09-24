package models.preprova;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PreprovaUsuarioResponseModel {

	private Integer idPreProva;
	private Integer idCandidato;
	private Integer notaPreProva;
	private String observacao;
	private String acao;
}
