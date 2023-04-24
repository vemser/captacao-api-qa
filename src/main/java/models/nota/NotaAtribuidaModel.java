package models.nota;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotaAtribuidaModel {

    private String Email;
    private Double Nota;
    private String Status;
}