package br.com.dbccompany.vemser.captacao.dto.formulario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties
public class FormularioDTO {

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
    private String configuracoes;
    private String efetivacao;
    private String genero;
    private String orientacao;
    private String disponibilidade;
    private List<TrilhaDTO> trilhas;
    private Integer imagemConfigPc;
    private String importancia;

}
