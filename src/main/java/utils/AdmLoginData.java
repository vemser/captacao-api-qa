package utils;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdmLoginData {

    private String username = System.getProperty("usernameAdm"); //Busca variável de ambiente com mesmo nome
    private String password = System.getProperty("passwordAdm"); //Busca variável de ambiente com mesmo nome
}
