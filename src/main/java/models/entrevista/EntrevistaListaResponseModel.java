package models.entrevista;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EntrevistaListaResponseModel {

    private Integer totalElementos;
    private Integer quantidadePaginas;
    private Integer pagina;
    private Integer tamanho;
    private List<EntrevistaCriacaoResponseModel> elementos;
}

/*
{
  "totalElementos": 9,
  "quantidadePaginas": 9,
  "pagina": 0,
  "tamanho": 1,
  "elementos": [
    {
      "candidatoEmail": "frederico.siqueira@bol.com.br",
      "dataEntrevista": "2023-04-14T18:47:02.602",
      "observacoes": "string",
      "avaliado": "T",
      "idEntrevista": 3,
      "candidatoDTO": {
        "idCandidato": 1,
        "nome": "DJALMA MARTINS FILHO",
        "dataNascimento": "1977-08-13",
        "email": "frederico.siqueira@bol.com.br",
        "telefone": "(38) 95690-5023",
        "rg": "11.027.125-7",
        "cpf": "212.755.567-87",
        "estado": "RO",
        "cidade": "São José de Mipibu",
        "nota": null,
        "pcd": "Não possui",
        "observacoes": null,
        "notaEntrevistaComportamental": 0,
        "notaEntrevistaTecnica": 0,
        "ativo": "T",
        "parecerComportamental": null,
        "parecerTecnico": null,
        "media": null,
        "linguagens": [],
        "edicao": {
          "idEdicao": 1,
          "nome": "VEMSER_1"
        },
        "formulario": {
          "idFormulario": 1,
          "matriculado": "T",
          "curso": "Analise e Desenvolvimento de Software",
          "turno": "NOITE",
          "instituicao": "PUC",
          "github": "https://github.com/link-github",
          "linkedin": "https://linkedin.com/",
          "desafios": "T",
          "problema": "T",
          "reconhecimento": "T",
          "altruismo": "T",
          "resposta": "Outro",
          "curriculo": 1,
          "lgpd": "T",
          "prova": "T",
          "ingles": "Não possuo",
          "espanhol": "Não possuo",
          "neurodiversidade": "TDAH",
          "etnia": "BRANCO",
          "efetivacao": "T",
          "genero": "Mulher Cis",
          "orientacao": "Heterosexual",
          "disponibilidade": "T",
          "trilhas": [
            {
              "nome": "QA",
              "idTrilha": 3
            },
            {
              "nome": "FRONTEND",
              "idTrilha": 1
            }
          ],
          "imagemConfigPc": null,
          "importancia": "importancia"
        }
      },
      "gestorDTO": {
        "idGestor": 2,
        "nome": "henrique soares",
        "email": "henrique.soares",
        "cargosDto": [
          {
            "idCargo": 2,
            "nome": "ROLE_INSTRUTOR"
          }
        ],
        "ativo": "T"
      },
      "legenda": "PENDENTE",
      "aprovado": null
    }
  ]
}
 */
