package models.edicao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EdicaoResponse extends EdicaoModel{
    private Integer idEdicao;
    private String message;
    private List<EdicaoResponse> edicoes;
}
