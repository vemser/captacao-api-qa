package models.inscricao;

import lombok.*;
import models.candidato.CandidatoCriacaoResponseModel;
import models.formulario.FormularioCriacaoResponseModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InscricaoModel {

    private Integer idInscricao;
    private CandidatoCriacaoResponseModel candidato;
    private FormularioCriacaoResponseModel formulario;
    private String dataInscricao;
    private String avaliado;
}
