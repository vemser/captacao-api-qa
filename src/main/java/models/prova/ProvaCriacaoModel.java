package models.prova;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProvaCriacaoModel {
    private Integer idProva;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;
    private Integer versaoProva;
    private String tituloProva;
    private String enunciadoProva;
    private Integer idEdicao;
    private List<Integer> idsLinguagens;
    private List<Integer> idQuestoes;
}