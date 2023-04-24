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
public class CandidatoCriacaoResponseModel {

    private Integer idCandidato;
    private String nome;
    private String dataNascimento;
    private String email;
    private String telefone;
    private String rg;
    private String cpf;
    private String estado;
    private String cidade;
    private Double nota;
    private String pcd;
    private String observacoes;
    private Double notaProva;
    private Double notaEntrevistaComportamental;
    private Double notaEntrevistaTecnica;
    private String ativo;
    private String parecerComportamental;
    private String parecerTecnico;
    private Double media;
    private List<LinguagemModel> linguagens;
    private EdicaoModel edicao;
    private FormularioCriacaoResponseModel formulario;
    private Integer imagem;
}

/*
{
    "idCandidato": null,
    "nome": "NOME SOBRENOME",
    "dataNascimento": "1959-12-29",
    "email": "sanofaj597@fitzola.com",
    "telefone": "(93) 98950-9498",
    "rg": "11.027.125-7",
    "cpf": "441.637.232-93",
    "estado": "PE",
    "cidade": "Arauá",
    "pcd": "Não possui",
    "ativo": "T",
    "linguagens": [
        "PYTHON"
    ],
    "edicao": {
        "idEdicao": 442,
        "nome": "VEMSER_438"
    },
    "formulario": 452,
    "observacoes": null,
    "notaEntrevistaComportamental": null,
    "notaEntrevistaTecnica": null,
    "parecerComportamental": null,
    "parecerTecnico": null,
    "media": null
}
 */



