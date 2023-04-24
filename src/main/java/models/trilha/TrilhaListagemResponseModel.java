package models.trilha;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TrilhaListagemResponseModel {

    private List<TrilhaModel> listaDeTrilhas;
}
