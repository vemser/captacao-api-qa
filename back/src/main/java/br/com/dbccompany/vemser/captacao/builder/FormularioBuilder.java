package br.com.dbccompany.vemser.captacao.builder;

import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.TipoTurno;

import java.util.List;

public class FormularioBuilder {

    public FormularioCreateDTO criarFormulario() {
        return FormularioCreateDTO.builder()
                .matriculadoBoolean(true)
                .curso("Sistema da Informação")
                .turno(TipoTurno.NOITE.toString())
                .instituicao("PUC Minas")
                .github("github")
                .linkedin("linkedin")
                .desafiosBoolean(true)
                .problemaBoolean(true)
                .reconhecimentoBoolean(true)
                .altruismoBoolean(true)
                .resposta("Resposta")
                .lgpdBoolean(true)
                .provaBoolean(true)
                .ingles("Inglês")
                .espanhol("Espanhol")
                .neurodiversidade("Neurodiversidade")
                .configuracoes("Configuracoes")
                .efetivacaoBoolean(true)
                .disponibilidadeBoolean(true)
                .genero("Genero")
                .orientacao("Orientação")
                .trilhas(List.of(1, 2))
                .importancia("Importancia")
                .build();
    }

}
