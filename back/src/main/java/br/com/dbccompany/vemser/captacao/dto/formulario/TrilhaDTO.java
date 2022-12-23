package br.com.dbccompany.vemser.captacao.dto.formulario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties
public class TrilhaDTO {

    private String nome;
    private Integer idTrilha;

}
