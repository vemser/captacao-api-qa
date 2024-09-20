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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidatoModel {

    public Integer totalElementos;
    public Integer quantidadePaginas;
    public Integer pagina;
    public Integer tamanho;
    public List<CandidatoCriacaoResponseModel> elementos;
    public Integer idCandidato;
    public String nome;
    public String dataNascimento;
    public String email;
    public String telefone;
    public String rg;
    public String cpf;
    public String estado;
    public String cidade;
    public String StatusCandidato;
    public String pcd;
    public Object observacoes;
    public Integer notaProva;
    public Integer notaEntrevistaComportamental;
    public Integer notaEntrevistaTecnica;
    public String ativo;
    public String parecerComportamental;
    public String parecerTecnico;
    public Integer media;
    public List<LinguagemModel> linguagens;
    public EdicaoModel edicao;
    public FormularioCriacaoResponseModel formulario;
    public Object imagem;
    public List<String> preProvasUsuario;
}
