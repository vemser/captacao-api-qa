package br.com.dbccompany.vemser.captacao.utils;

import net.datafaker.Faker;

import java.util.Locale;

public class Utils {

    public static Faker faker = new Faker(new Locale("pt-BR"));

    /*public enum Env {
        DEV, HML, PRD
    }

/*    public static Env envStringToEnum() {
        try {
            return Env.valueOf(getEnv());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ambiente não existente: " + getEnv());
        }
    }*/

    public static String getEnv() {
        return Manipulation.getProp().getProperty("prop.env");
    }

/*    public static String convertPessoaToJson(PessoaCreateDTO pessoa) {
        return new Gson().toJson(pessoa);
    }*/

/*    public static PessoaCreateDTO convertJsonToPessoa(String toJson) {
        return new Gson().fromJson(toJson, PessoaCreateDTO.class);
    }*/

}
