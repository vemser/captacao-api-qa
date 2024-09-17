package models.formulario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import models.trilha.TrilhaModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormularioModel {

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
    private Integer curriculo;
    private String lgpd;
    private String prova;
    private String ingles;
    private String espanhol;
    private String neurodiversidade;
    private String etnia;
    private String efetivacao;
    private String genero;
    private String orientacao;
    private String disponibilidade;
    private List<TrilhaModel> trilhas;
    private Integer imagemConfigPc;
    private String Importancia;
}
