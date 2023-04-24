package models.prova;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProvaCriacaoResponseModel {

    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;
    private Integer idCandidato;
    private Integer idProva;
    private Integer notaProva;
}
