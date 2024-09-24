package models.trilha;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrilhaResponse extends TrilhaModel{
    private Integer idTrilha;
    private String message;
}
