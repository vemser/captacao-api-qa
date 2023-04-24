package models.entrevista;

import lombok.*;
import models.candidato.CandidatoCriacaoResponseModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.gestor.GestorModel;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EntrevistaCriacaoResponseModel {

    private String candidatoEmail;
    private LocalDateTime dataEntrevista;
    private String observacoes;
    private String avaliado;
    private Integer idEntrevista;
    private CandidatoCriacaoResponseModel candidatoDTO;
    private FormularioCriacaoResponseModel formulario;
    private GestorModel gestorDTO;
    private String legenda;
    private String aprovado;
}