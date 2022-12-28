package br.com.dbccompany.vemser.captacao.builder;

import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.TipoTurno;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class FormularioBuilder {

    public FormularioCreateDTO criarFormulario() {
        return FormularioCreateDTO.builder()
                .matriculadoBoolean(true)
                .curso("Sistema da Informação")
                .turno(TipoTurno.NOITE.toString())
                .instituicao("PUC Minas")
                .github("https://github.com/testeqa")
                .linkedin("https://www.linkedin.com/in/testeqa/")
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
                .genero("Gênero")
                .orientacao("Orientação sexual")
                .trilhas(List.of(1, 2))
                .importancia("Importância")
                .build();
    }

    public FormularioCreateDTO criarFormularioSemPreencherCamposObrigatorios() {
        FormularioCreateDTO formularioInvalido = criarFormulario();
        formularioInvalido.setCurso(StringUtils.EMPTY);
        formularioInvalido.setTurno(StringUtils.EMPTY);
        formularioInvalido.setInstituicao(StringUtils.EMPTY);
        formularioInvalido.setIngles(StringUtils.EMPTY);
        formularioInvalido.setEspanhol(StringUtils.EMPTY);
        formularioInvalido.setConfiguracoes(StringUtils.EMPTY);
        formularioInvalido.setGenero(StringUtils.EMPTY);
        formularioInvalido.setOrientacao(StringUtils.EMPTY);
        formularioInvalido.setTrilhas(List.of());
        formularioInvalido.setImportancia(StringUtils.EMPTY);

        return formularioInvalido;
    }

    public FormularioCreateDTO criarFormularioSemMatricula() {
        FormularioCreateDTO formularioInvalido = criarFormulario();
        formularioInvalido.setMatriculadoBoolean(false);

        return formularioInvalido;
    }

    public FormularioCreateDTO atualizarFormulario() {
        return FormularioCreateDTO.builder()
                .matriculadoBoolean(true)
                .curso("Engenharia da Computação")
                .turno(TipoTurno.MANHA.toString())
                .instituicao("UFBA")
                .github("https://github.com/testeqa-atualizado")
                .linkedin("https://www.linkedin.com/in/testeqa-atualizado/")
                .desafiosBoolean(false)
                .problemaBoolean(false)
                .reconhecimentoBoolean(false)
                .altruismoBoolean(false)
                .resposta("Resposta atualizada")
                .lgpdBoolean(false)
                .provaBoolean(false)
                .ingles("Inglês atualizado")
                .espanhol("Espanhol atualizado")
                .neurodiversidade("Neurodiversidade atualizado")
                .configuracoes("Configuracoes atualizado")
                .efetivacaoBoolean(false)
                .disponibilidadeBoolean(false)
                .genero("Gênero  atualizado")
                .orientacao("Orientação sexual atualizado")
                .trilhas(List.of(3))
                .importancia("Importância atualizado")
                .build();
    }

    public FormularioCreateDTO atualizarFormularioSemPreencherCamposObrigatorios() {
        FormularioCreateDTO formularioInvalido = atualizarFormulario();
        formularioInvalido.setCurso(StringUtils.EMPTY);

        return formularioInvalido;
    }

}
