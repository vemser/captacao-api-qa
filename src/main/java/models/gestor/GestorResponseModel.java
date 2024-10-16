package models.gestor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GestorResponseModel {

	private Integer idGestor;
	private String nome;
	private String email;
	private String cidade;
	private String estado;
	private String ativo;
	private String genero;
}