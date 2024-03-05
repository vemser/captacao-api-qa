package models.prova;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProvaEditarModel {
    private String tituloProva;
    private String enunciadoProva;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<Integer> idsQuestoes;
}
