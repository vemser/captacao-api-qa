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

        loginModel.setUsername(Manipulation.getProp().getProperty("ADMIN_LOGIN"));
        loginModel.setPassword(Manipulation.getProp().getProperty("ADMIN_PSW"));

        authClient.logar(loginModel);
    }

    public static void usuarioAluno() {
        LoginModel loginModel = new LoginModel();

        loginModel.setUsername(Manipulation.getProp().getProperty("ALUNO_LOGIN"));
        loginModel.setPassword(Manipulation.getProp().getProperty("ALUNO_PSW"));

        authClient.logar(loginModel);
    }

    public static void usuarioGestaoDePessoas() {
        LoginModel loginModel = new LoginModel();

        loginModel.setUsername(Manipulation.getProp().getProperty("GP_LOGIN"));
        loginModel.setPassword(Manipulation.getProp().getProperty("GP_PSW"));

        authClient.logar(loginModel);
    }

    public static void usuarioColaborador() {
        LoginModel loginModel = new LoginModel();

        loginModel.setUsername(Manipulation.getProp().getProperty("COLABORADOR_LOGIN"));
        loginModel.setPassword(Manipulation.getProp().getProperty("COLABORADOR_PSW"));

        authClient.logar(loginModel);
    }

    public static void usuarioInstrutor() {
        LoginModel loginModel = new LoginModel();

        loginModel.setUsername(Manipulation.getProp().getProperty("INSTRUTOR_LOGIN"));
        loginModel.setPassword(Manipulation.getProp().getProperty("INSTRUTOR_PSW"));

        authClient.logar(loginModel);
    }

    public static void usuarioGestor() {
        LoginModel loginModel = new LoginModel();

        loginModel.setUsername(Manipulation.getProp().getProperty("GESTOR_LOGIN"));
        loginModel.setPassword(Manipulation.getProp().getProperty("GESTOR_PSW"));

        authClient.logar(loginModel);
    }

    public static void usuarioInvalido() {
        LoginModel loginModel = new LoginModel();

        loginModel.setUsername(Manipulation.getProp().getProperty("INVALIDO_LOGIN"));
        loginModel.setPassword(Manipulation.getProp().getProperty("INVALIDO_PSW"));

        authClient.logar(loginModel);
    }
}
