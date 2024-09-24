package models.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.cargo.CargoModel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioModel {
    private Integer idGestor;
    private String nome;
    private String email;
    private List<CargoModel> cargosDto;
    private String ativo;
}
