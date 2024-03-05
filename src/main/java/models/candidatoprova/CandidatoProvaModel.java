package models.candidatoprova;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidatoProvaModel {
    private Integer idCandidato;
    private Integer idProva;
    private String dataInicio;
    private String dataFim;
}
