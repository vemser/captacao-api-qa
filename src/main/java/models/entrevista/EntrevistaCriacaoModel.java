package models.entrevista;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EntrevistaCriacaoModel {

    public Integer idEntrevista;
    private String candidatoEmail;
    private String dataEntrevista;
    private String observacoes;
    private String avaliado;
}
