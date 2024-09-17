package models.formulario;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FormularioCriacaoModel {

    private Integer idFormulario;
    private Boolean matriculadoBoolean;
    private String curso;
    private String turno;
    private String instituicao;
    private String github;
    private String linkedin;
    private Boolean desafiosBoolean;
    private Boolean problemaBoolean;
    private Boolean reconhecimentoBoolean;
    private Boolean altruismoBoolean;
    private String resposta;
    private Boolean lgpdBoolean;
    private Boolean provaBoolean;
    private String ingles;
    private String espanhol;
    private String neurodiversidade;
    private String etnia;
    private Boolean efetivacaoBoolean;
    private Boolean disponibilidadeBoolean;
    private String genero;
    private String orientacao;
    private List<String> trilhas;
    private String importancia;
    private Integer qtdSemestres;
    private Integer semestreAtual;
    private String tipoGraduacao;
    private String descConfigPc;
}
