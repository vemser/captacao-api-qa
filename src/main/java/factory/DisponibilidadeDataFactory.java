package factory;

import models.disponibilidade.DisponibilidadeModel;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class DisponibilidadeDataFactory {
    private static Faker faker = new Faker();

    public static DisponibilidadeModel disponibilidadeIdGestorInexistente() {
        Integer idGestor = faker.random().nextInt(10000, 99999);

        return novaDisponibilidade(idGestor);
    }

    public static DisponibilidadeModel disponibilidadeValida(Integer idGestor) {
        return novaDisponibilidade(idGestor);
    }

    public static DisponibilidadeModel disponibilidadeDataPassado(Integer idGestor) {

        DisponibilidadeModel disponibilidade = novaDisponibilidade(idGestor);

        disponibilidade.setDataEntrevista("1990-01-01");

        return disponibilidade;
    }

    public static DisponibilidadeModel disponibilidadeHorariosInvalidos(Integer idGestor) {

        DisponibilidadeModel disponibilidade = novaDisponibilidade(idGestor);

        disponibilidade.setHoraInicio("43:69:78");
        disponibilidade.setHoraFim("43:79:68");

        return disponibilidade;
    }

    public static DisponibilidadeModel disponibilidadeDataInvalida(Integer idGestor) {

        DisponibilidadeModel disponibilidade = novaDisponibilidade(idGestor);

        disponibilidade.setDataEntrevista("1990-45-90");

        return disponibilidade;
    }

    private static DisponibilidadeModel novaDisponibilidade(Integer idGestor) {

        DisponibilidadeModel disponibilidade = new DisponibilidadeModel();

        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 17, 59);
        LocalDateTime randomDateTime = randomDateTimeBetween(start, end);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String dataEntrevista = randomDateTime.format(dateFormatter);
        String horaInicio = randomDateTime.format(timeFormatter);
        String horaFim = randomDateTime.plusMinutes(30).format(timeFormatter);

        disponibilidade.setIdGestor(idGestor);
        disponibilidade.setDataEntrevista(dataEntrevista);
        disponibilidade.setHoraInicio(horaInicio);
        disponibilidade.setHoraFim(horaFim);
        disponibilidade.setMarcado(false);
		disponibilidade.setTrilha("QA");

        return disponibilidade;
    }

    public static LocalDateTime randomDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        long startEpoch = start.toEpochSecond(java.time.ZoneOffset.UTC);
        long endEpoch = end.toEpochSecond(java.time.ZoneOffset.UTC);
        long randomEpoch = ThreadLocalRandom.current().nextLong(startEpoch, endEpoch);
        return LocalDateTime.ofEpochSecond(randomEpoch, 0, java.time.ZoneOffset.UTC);
    }

}
