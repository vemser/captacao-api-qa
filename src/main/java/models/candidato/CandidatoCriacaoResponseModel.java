package models.candidato;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.formulario.FormularioModel;
import models.linguagem.LinguagemModel;
import models.preprova.PreprovaUsuarioResponseModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CandidatoCriacaoResponseModel {

	private int idCandidato;
	private String nome;
	private String dataNascimento;
	private String email;
	private String telefone;
	private String rg;
	private String cpf;
	private String estado;
	private String cidade;
	private String statusCandidato;
	private String pcd;
	private String observacoes;
	private double notaProva;
	private double notaEntrevistaComportamental;
	private double notaEntrevistaTecnica;
	private String ativo;
	private String parecerComportamental;
	private String parecerTecnico;
	private Double media;
	private List<LinguagemModel> linguagens;
	private EdicaoModel edicao;
	private FormularioCriacaoResponseModel formulario;
	private String imagem;
	private List<PreprovaUsuarioResponseModel> preProvasUsuario;
}



