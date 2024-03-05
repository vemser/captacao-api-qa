package models.edicao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EdicaoResponse extends EdicaoModel{
    private Integer idEdicao;
    private String message;
}
