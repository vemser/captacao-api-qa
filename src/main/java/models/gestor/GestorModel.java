package models.gestor;

import lombok.*;
import models.cargo.CargoModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GestorModel {

    private Integer idGestor;
    private String nome;
    private String email;
    private List<CargoModel> cargosDto;
    private String ativo;
}
