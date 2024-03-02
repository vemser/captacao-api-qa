package models.prova;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProvaCriacaoResponseModel {
    private Integer idCandidatoProva;
    private String mensagem;
}
