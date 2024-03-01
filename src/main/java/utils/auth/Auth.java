package utils.auth;

import client.auth.AuthClient;
import models.login.LoginModel;
import utils.config.Manipulation;

public class Auth {
    private static final AuthClient authClient = new AuthClient();

    private Auth() {

    }

    public static void obterTokenComoAdmin() {
        LoginModel loginModel = new LoginModel();

        loginModel.setUsername(Manipulation.getProp().getProperty("USER_LOGIN"));
        loginModel.setPassword(Manipulation.getProp().getProperty("USER_PSW"));

        authClient.logar(loginModel);
    }

    public static void usuarioNaoAutenticado() {
        LoginModel loginModel = new LoginModel();

        loginModel.setUsername(Manipulation.getProp().getProperty("USER_NOT_AUTHENTICATED"));
        loginModel.setPassword(Manipulation.getProp().getProperty("USER_NOT_AUTHENTICATED.USER_PSW"));

        authClient.loginInvalido(loginModel);
    }
}
