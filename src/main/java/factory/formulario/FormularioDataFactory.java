package factory.formulario;

import factory.trilha.TrilhaDataFactory;
import models.formulario.FormularioCriacaoModel;
import models.formulario.JSONListaFormularioResponse;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import client.formulario.FormularioClient;
import utils.config.Tools;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FormularioDataFactory {

    private static Faker faker = new Faker(new Locale("pt-BR"));
    private static FormularioClient formularioClient = new FormularioClient();
    private static TrilhaDataFactory trilhaDataFactory = new TrilhaDataFactory();
    private static Random random = new Random();
    private static List<String> turnosValidos = Arrays.asList("MANHA", "TARDE", "NOITE");
    private static List<String> etniasValidas = Arrays.asList("AMARELO", "BRANCO", "INDIGENA", "PARDO", "PRETO", "NAO_DECLARADO");

    public static FormularioCriacaoModel formularioValido(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setTrilhas(trilhas);

        return formulario;
    }

    public static FormularioCriacaoModel formularioComInstituicaoAtualizada(FormularioCriacaoModel formulario) {

        formulario.setInstituicao(faker.lorem().sentence(3));

        return formulario;
    }

    public static FormularioCriacaoModel formularioComMatriculadoFalse(List<String> trilhas) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setTrilhas(trilhas);

        formulario.setMatriculadoBoolean(false);

        return formulario;
    }


    public static FormularioCriacaoModel formularioValidoComTrilhaExistente(String nomeDeTrilhaExistente) {

        FormularioCriacaoModel formulario = novoFormulario();
        formulario.setTrilhas(Arrays.asList("QA"));

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
        formulario.setCurso(Tools.removerCaracteresEspeciais(faker.lorem().sentence()));
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