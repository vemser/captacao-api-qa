package br.com.dbccompany.vemser.captacao.dto.candidato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties
public class CandidatoCreateDTO {

    private String nome;
    private String dataNascimento;
    private String email;
    private String telefone;
    private String rg;
    private String cpf;
    private String estado;
    private String cidade;
    private String ativo;
    private List<LinguagemDTO> linguagens;
    private EdicaoDTO edicao;
    private Integer formulario;
    private Boolean pcdboolean;

}
