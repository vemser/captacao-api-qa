package br.com.dbccompany.vemser.captacao.dto.entrevista;

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
public class EntrevistaCreateDTO {

    private String candidatoEmail;
    private String usuarioEmail;
    private String dataEntrevista;
    private String observacoes;
    private String avaliado;
}
