package br.com.dbccompany.vemser.tests.base;

import io.restassured.RestAssured;
import models.candidato.CandidatoCriacaoResponseModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.linguagem.LinguagemModel;
import models.trilha.TrilhaModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import service.*;

public abstract class BaseTest {
    @BeforeAll
    public static void setUp() {

        //RestAssured.baseURI =  "http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-back";
        RestAssured.port = 8080;

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterAll
    public static void cleanUp() {

    }
}
