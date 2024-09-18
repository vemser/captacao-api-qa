package factory.formulario;

import client.formulario.FormularioClient;
import models.formulario.FormularioCriacaoModel;
import models.formulario.JSONListaFormularioResponse;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import utils.config.Tools;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FormularioDataFactory {

    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final Random random = new Random();
    private static final List<String> turnosValidos = Arrays.asList("MANHA", "TARDE", "NOITE");
    private static final List<String> etniasValidas = Arrays.asList("AMARELO", "BRANCO", "INDIGENA", "PARDO", "PRETO", "NAO_DECLARADO");
    private static final String TURNO_INVALIDO = "TARDE";

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

    public static FormularioCriacaoModel formularioTurnoInvalido(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setTurno(TURNO_INVALIDO);

        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioSemestreNegativo(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setSemestreAtual(-1);

        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioQntSemestresNegativo(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setQtdSemestres(-1);

        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioInstituicaoNula(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setInstituicao(null);

        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioComInstituicaoAtualizada(FormularioCriacaoModel formulario) {

        formulario.setInstituicao(faker.university().name());

        return formulario;
    }

    public static FormularioCriacaoModel formularioValidoComTrilhaExistente(String nomeDeTrilhaExistente) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setTrilhas(List.of(nomeDeTrilhaExistente));

        return formulario;
    }

    public static Integer idFormularioNaoCadastrado() {

        Integer idUltimoFormulario = formularioClient.listarNumDeFormulariosOrdemDecrescente(1)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(JSONListaFormularioResponse.class)
                    .getElementos()
                    .get(0)
                    .getIdFormulario();

        return idUltimoFormulario + 100;
    }

    private static FormularioCriacaoModel novoFormulario() {
        String linkedinUrl = "https://www.linkedin.com";
        String githubUrl = "https://www.github.com";

        FormularioCriacaoModel formulario = new FormularioCriacaoModel();
        formulario.setMatriculadoBoolean(true);
        formulario.setCurso(Tools.removerCaracteresEspeciais(faker.educator().course()));
        formulario.setTurno(turnosValidos.get(random.nextInt(turnosValidos.size())));
        formulario.setInstituicao(Tools.removerCaracteresEspeciais(faker.university().name()));
        formulario.setGithub(githubUrl);
        formulario.setLinkedin(linkedinUrl);
        formulario.setDesafiosBoolean(random.nextBoolean());
        formulario.setProblemaBoolean(random.nextBoolean());
        formulario.setReconhecimentoBoolean(random.nextBoolean());
        formulario.setAltruismoBoolean(random.nextBoolean());
        formulario.setResposta(faker.lorem().word());
        formulario.setLgpdBoolean(random.nextBoolean());
        formulario.setProvaBoolean(random.nextBoolean());
        formulario.setIngles(faker.lorem().word());
        formulario.setEspanhol(faker.lorem().word());
        formulario.setNeurodiversidade(faker.lorem().word());
        formulario.setEtnia(etniasValidas.get(random.nextInt(etniasValidas.size())));
        formulario.setEfetivacaoBoolean(random.nextBoolean());
        formulario.setDisponibilidadeBoolean(random.nextBoolean());
        formulario.setGenero(faker.demographic().sex());
        formulario.setOrientacao(faker.demographic().sex());
        formulario.setImportancia(faker.lorem().word());

        return formulario;
    }
}