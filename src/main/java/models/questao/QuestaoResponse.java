package models.questao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestaoResponse extends QuestaoPraticaModel {
    private String mensagem;
    private String id;
}
