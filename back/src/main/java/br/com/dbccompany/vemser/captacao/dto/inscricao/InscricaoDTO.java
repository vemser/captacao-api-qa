package br.com.dbccompany.vemser.captacao.dto.inscricao;

import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoDTO;
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
public class InscricaoDTO {
    private Integer idInscricao;
    private CandidatoDTO candidato;
    private String dataInscricao;
    private String avaliacao;
}
