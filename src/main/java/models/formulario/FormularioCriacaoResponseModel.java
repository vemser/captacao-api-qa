package models.formulario;

import groovy.lang.GString;
import lombok.*;
import models.trilha.TrilhaModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FormularioCriacaoResponseModel {

    private Integer idFormulario;
    private String matriculado;
    private String curso;
    private String turno;
    private String instituicao;
    private String github;
    private String linkedin;
    private String desafios;
    private String problema;
    private String reconhecimento;
    private String altruismo;
    private String resposta;
    private Object curriculo;
    private String lgpd;
    private String prova;
    private String ingles;
    private String espanhol;
    private String neurodiversidade;
    private String efetivacao;
    private String genero;
    private String orientacao;
    private String disponibilidade;
    private List<TrilhaModel> trilhas;
    private Object imagemConfigPc;
    private Integer qtdSemestres;
    private Integer semestreAtual;
    private Integer tipoGraduacao;
    private String descConfigPc;
    private String importancia;
}