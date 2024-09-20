package models.prova;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PreProvasUsuarioResponseModel {
    private Integer idPreProva;
    private Integer idCandidato;
    private Integer notaPreProva;
    private String observacao;
}
