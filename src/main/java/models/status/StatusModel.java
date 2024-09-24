package models.status;

import lombok.Data;
import models.edicao.EdicaoModel;
import models.formulario.FormularioModel;
import models.linguagem.LinguagemModel;
import models.preprova.PreprovaUsuarioResponseModel;

import java.util.List;

@Data
public class StatusModel {

	private Integer idCandidato;
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
	private Integer notaProva;
	private Integer notaEntrevistaComportamental;
	private Integer notaEntrevistaTecnica;
	private String ativo;
	private String parecerComportamental;
	private String parecerTecnico;
	private String media;
	private List<LinguagemModel> linguagens;
	private EdicaoModel edicao;
	private FormularioModel formulario;
	private Object imagem;
	private List<PreprovaUsuarioResponseModel> preProvasUsuario;
}
