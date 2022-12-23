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
public class FormularioCreateDTO {

    private boolean matriculadoBoolean;
    private String curso;
    private String turno;
    private String instituicao;
    private String github;
    private String linkedin;
    private boolean desafiosBoolean;
    private boolean problemaBoolean;
    private boolean reconhecimentoBoolean;
    private boolean altruismoBoolean;
    private String resposta;
    private boolean lgpdBoolean;
    private boolean provaBoolean;
    private String ingles;
    private String espanhol;
    private String neurodiversidade;
    private String configuracoes;
    private boolean efetivacaoBoolean;
    private boolean disponibilidadeBoolean;
    private String genero;
    private String orientacao;
    private List<Integer> trilhas;
    private String importancia;

}
