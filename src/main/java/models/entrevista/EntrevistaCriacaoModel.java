package models.entrevista;

import lombok.*;

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
