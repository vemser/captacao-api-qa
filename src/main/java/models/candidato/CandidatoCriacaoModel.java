package models.candidato;

import lombok.*;
import models.edicao.EdicaoModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CandidatoCriacaoModel {

    private Integer idCandidato;
    private String nome;
    private String dataNascimento;
    private String email;
    private String telefone;
    private String rg;
    private String cpf;
    private String estado;
    private String cidade;
    private String pcd;
    private String ativo;
    private List<String> linguagens;
    private EdicaoModel edicao;
    private Integer formulario;
    private String observacoes;
    private Double notaEntrevistaComportamental;
    private Double notaEntrevistaTecnica;
    private String parecerComportamental;
    private String parecerTecnico;
    private Double media;
}