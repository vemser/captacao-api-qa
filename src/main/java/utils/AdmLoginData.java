package utils;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdmLoginData {
    private String username; //Busca variável de ambiente com mesmo nome
    private String password; //Busca variável de ambiente com mesmo nome
}