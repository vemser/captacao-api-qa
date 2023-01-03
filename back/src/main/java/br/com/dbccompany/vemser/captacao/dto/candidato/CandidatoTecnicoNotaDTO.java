package br.com.dbccompany.vemser.captacao.dto.candidato;

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
public class CandidatoTecnicoNotaDTO {
    private Number notaTecnico;
    private String parecerTecnico;
}
