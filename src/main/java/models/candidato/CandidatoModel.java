package models.candidato;

import lombok.*;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.linguagem.LinguagemModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CandidatoModel {

    public Integer idCandidato;
    public String nome;
    public String dataNascimento;
    public String email;
    public String telefone;
    public String rg;
    public String cpf;
    public String estado;
    public String cidade;
    private Double nota;
    public String pcd;
    public Object observacoes;
    public Double notaProva;
    public Double notaEntrevistaComportamental;
    public Double notaEntrevistaTecnica;
    public String ativo;
    public String parecerComportamental;
    public String parecerTecnico;
    public Double media;
    public List<LinguagemModel> linguagens;
    public EdicaoModel edicao;
    public FormularioCriacaoResponseModel formulario;
    public Object imagem;
}
