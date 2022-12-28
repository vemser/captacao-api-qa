package br.com.dbccompany.vemser.captacao.utils;

import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoNotaDTO;
import br.com.dbccompany.vemser.captacao.dto.formulario.FormularioCreateDTO;
import com.google.gson.Gson;
import net.datafaker.Faker;

import java.util.Locale;

public class Utils {

    public static Faker faker = new Faker(new Locale("pt-BR"));

    public static String getBaseUrl() {
        String baseUrl = "http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-back";

        return baseUrl;
    }

    public static String convertFormularioToJson(FormularioCreateDTO formulario) {
        return new Gson().toJson(formulario);
    }

    public static String convertCandidatoToJson(CandidatoCreateDTO candidato) {
        return new Gson().toJson(candidato);
    }

    public static String convertCandidatoNotaToJson(CandidatoNotaDTO nota) {
        return new Gson().toJson(nota);
    }
/*    public static PessoaCreateDTO convertJsonToPessoa(String toJson) {
        return new Gson().fromJson(toJson, PessoaCreateDTO.class);
    }*/

}
