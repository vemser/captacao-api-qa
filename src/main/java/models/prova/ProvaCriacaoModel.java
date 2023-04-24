package models.prova;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProvaCriacaoModel {

    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;
}