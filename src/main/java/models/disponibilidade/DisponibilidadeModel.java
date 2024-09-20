package models.disponibilidade;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DisponibilidadeModel {

    private Integer idCandidato;
    private Integer idGestor;
    private String dataEntrevista;
    private String horaInicio;
    private String horaFim;
    private boolean marcado;

}
