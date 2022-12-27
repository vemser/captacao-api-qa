package br.com.dbccompany.vemser.captacao.dto.candidato;

import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioDTO;
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
public class CandidatoDTO {
    private Integer idCandidato;
    private String nome;
    private String dataNascimento;
    private String email;
    private String telefone;
    private String rg;
    private String cpf;
    private String estado;
    private String cidade;
    private String observacoes;
    private Number notaProva;
    private Number notaEntrevistaComportamental;
    private Number notaEntrevistaTecnica;
    private String ativo;
    private String parecerComportamental;
    private String parecerTecnico;
    private Number media;
    private List<LinguagemDTO> linguagens;
    private EdicaoDTO edicao;
    private FormularioDTO formulario;
    private boolean pcdboolean;
}
