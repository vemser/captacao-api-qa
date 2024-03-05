package factory.entrevista;

import models.entrevista.EntrevistaCriacaoModel;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

public class EntrevistaDataFactory {

    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static final Random random = new Random();

    public static EntrevistaCriacaoModel entrevistaCriacaoValida(String emailDoCandidato, Boolean avaliado, Integer idTrilha) {
        return novaEntrevistaCriacao(emailDoCandidato, avaliado, idTrilha);
    }

    public static EntrevistaCriacaoModel entrevistaValidaComDataEspecifica(Integer anoEntrevista, Integer mesEntrevista, String emailDoCandidato, Boolean avaliado, Integer idTrilha) {

        int horaAleatoria = random.nextInt(18);
        int minutoAleatorio = random.nextInt(60);
        int segundoAleatorio = random.nextInt(60);

        int diasNoFuturo = random.nextInt(1000);

        int diaFuturo = LocalDateTime.now().plusDays(diasNoFuturo).getDayOfMonth();

        LocalDateTime dataEntrevista = LocalDateTime.of(anoEntrevista, mesEntrevista, diaFuturo, horaAleatoria, minutoAleatorio, segundoAleatorio, 602000000);

        String candidatoAvaliado = avaliado ? "T" : "F";

        EntrevistaCriacaoModel entrevista = new EntrevistaCriacaoModel();
        entrevista.setCandidatoEmail(emailDoCandidato);
        entrevista.setDataEntrevista(dataEntrevista);
        entrevista.setObservacoes(faker.lorem().sentence(4));
        entrevista.setAvaliado(candidatoAvaliado);
        entrevista.setIdTrilha(idTrilha);

        return entrevista;
    }

    public static EntrevistaCriacaoModel entrevistaCriacaoValidaComDadosAtualizados(EntrevistaCriacaoModel entrevista, String observacoes, Boolean avaliado) {

        String candidatoAvaliado = avaliado ? "T" : "F";

        entrevista.setObservacoes(observacoes);
        entrevista.setAvaliado(candidatoAvaliado);

        return entrevista;
    }

    private static EntrevistaCriacaoModel novaEntrevistaCriacao(String emailDoCandidato, Boolean avaliado, Integer idTrilha) {

        int horaAleatoria = random.nextInt(18);
        int minutoAleatorio = random.nextInt(60);
        int segundoAleatorio = random.nextInt(60);

        int diasNoFuturo = random.nextInt(1000);

        int diaFuturo = LocalDateTime.now().plusDays(diasNoFuturo).getDayOfMonth();
        int mesFuturo = LocalDateTime.now().plusDays(diasNoFuturo).getMonthValue();
        int anoFuturo = LocalDateTime.now().plusDays(diasNoFuturo).getYear();

        LocalDateTime dataEntrevista = LocalDateTime.of(anoFuturo, mesFuturo, diaFuturo, horaAleatoria, minutoAleatorio, segundoAleatorio, 602000000);

        String candidatoAvaliado = avaliado ? "T" : "F";

        EntrevistaCriacaoModel entrevista = new EntrevistaCriacaoModel();
        entrevista.setCandidatoEmail(emailDoCandidato);
        entrevista.setDataEntrevista(dataEntrevista);
        entrevista.setObservacoes(faker.lorem().sentence(4));
        entrevista.setAvaliado(candidatoAvaliado);
        entrevista.setIdTrilha(idTrilha);

        return entrevista;
    }
}
