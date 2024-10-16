package factory;

import client.FormularioClient;
import models.formulario.FormularioCriacaoModel;
import models.formulario.JSONListaFormularioResponse;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import utils.config.Tools;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FormularioDataFactory {

    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final Random random = new Random();

    public static FormularioCriacaoModel formularioValido(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioNaoMatriculado(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setMatriculadoBoolean(false);

        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioInstituicaoNula(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setInstituicao(null);

        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioRespostaVazia(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setResposta(null);

        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioComInstituicaoAtualizada(FormularioCriacaoModel formulario) {

        formulario.setInstituicao(faker.university().name());

        return formulario;
    }

    public static Integer idFormularioNaoCadastrado() {

        Integer idUltimoFormulario = formularioClient.listarNumDeFormulariosOrdemDecrescente()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(JSONListaFormularioResponse.class)
                    .getElementos()
                    .get(0)
                    .getIdFormulario();

        return idUltimoFormulario + 10000;
    }

    private static FormularioCriacaoModel novoFormulario() {
        String linkedinUrl = "https://www.linkedin.com";
        String githubUrl = "https://www.github.com";

        FormularioCriacaoModel formulario = new FormularioCriacaoModel();
        formulario.setMatriculadoBoolean(true);
        formulario.setCurso(Tools.removerCaracteresEspeciais(faker.educator().course()));
        formulario.setTurno("NOITE");
        formulario.setInstituicao(Tools.removerCaracteresEspeciais(faker.university().name()));
        formulario.setGithub(githubUrl);
        formulario.setLinkedin(linkedinUrl);
        formulario.setDesafiosBoolean(true);
        formulario.setProblemaBoolean(true);
        formulario.setReconhecimentoBoolean(true);
        formulario.setAltruismoBoolean(true);
        formulario.setResposta(faker.lorem().word());
        formulario.setLgpdBoolean(true);
        formulario.setProvaBoolean(true);
        formulario.setIngles(faker.lorem().word());
        formulario.setEspanhol(faker.lorem().word());
        formulario.setNeurodiversidade(faker.lorem().word());
        formulario.setEfetivacaoBoolean(true);
        formulario.setDisponibilidadeBoolean(true);
        formulario.setGenero("F");
        formulario.setOrientacao(faker.demographic().sex());
        formulario.setImportancia(faker.lorem().word());

        return formulario;
    }
}