package br.com.dbccompany.vemser.captacao.utils;

import net.datafaker.Faker;

import java.util.Locale;

public class Utils {

    public static Faker faker = new Faker(new Locale("pt-BR"));

    public static String getBaseUrl() {
        String baseUrl = "http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-back";

        return baseUrl;
    }

/*    public static String convertPessoaToJson(PessoaCreateDTO pessoa) {
        return new Gson().toJson(pessoa);
    }*/

/*    public static PessoaCreateDTO convertJsonToPessoa(String toJson) {
        return new Gson().fromJson(toJson, PessoaCreateDTO.class);
    }*/

}
