package utils;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdmLoginData {
    private String username = System.getenv("USER_LOGIN"); //Busca variável de ambiente com mesmo nome
    private String password = System.getenv("USER_PSW"); //Busca variável de ambiente com mesmo nome
}