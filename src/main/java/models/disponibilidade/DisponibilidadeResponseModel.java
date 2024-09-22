package models.disponibilidade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import models.candidato.CandidatoModel;
import models.gestor.GestorResponseModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisponibilidadeResponseModel {
    private Integer idDisponibilidade;
    private CandidatoModel candidatoEntity;
    private GestorResponseModel gestorEntity;
    private String dataEntrevista;
    private String horaInicio;
    private String horaFim;
    private boolean marcado;
}
