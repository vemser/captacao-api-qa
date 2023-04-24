package models.relatorio;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RelatorioEstadoModel {

    private String estado;
    private Integer quantidade;
}
