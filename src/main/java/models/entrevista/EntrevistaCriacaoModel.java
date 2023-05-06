package models.entrevista;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EntrevistaCriacaoModel {

    private String candidatoEmail;
    private LocalDateTime dataEntrevista;
    private String observacoes;
    private String avaliado;
    private Integer idTrilha;
}
