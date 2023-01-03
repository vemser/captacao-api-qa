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
public class EntrevistaAtualizacaoDTO {

    private String dataEntrevista;
    private String cidade;
    private String estado;
    private String observacoes;
    private String email;
    private String avaliado;
}
