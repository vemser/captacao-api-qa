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
    private String dataEntrevista;
    private String observacoes;
    private String avaliado;
}
