package models.entrevista;

import lombok.*;
import models.candidato.CandidatoCriacaoResponseModel;
import models.gestor.GestorModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EntrevistaCriacaoResponseModel {

    private String candidatoEmail;
    private String dataEntrevista;
    private String observacoes;
    private String avaliado;
    private Integer idEntrevista;
    private CandidatoCriacaoResponseModel candidatoDTO;
    private GestorModel gestorDTO;
    private String legenda;
}