package br.com.dbccompany.vemser.tests.disponibilidade;

import client.DisponibilidadeClient;
import factory.DisponibilidadeDataFactory;
import models.disponibilidade.DisponibilidadeModel;
import models.disponibilidade.DisponibilidadeResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Endpoint de deletar disponibilidade")
public class DeletarDisponibilidadeTest {
    private static final DisponibilidadeClient disponibilidadeClient = new DisponibilidadeClient();
    private static List<DisponibilidadeResponseModel> disponibilidadeCadastrada;

    @BeforeAll
    public static void criarMassaDados(){

        DisponibilidadeModel disponibilidadeParaCadastrar = DisponibilidadeDataFactory.disponibilidadeValida(3);
        disponibilidadeCadastrada = disponibilidadeClient.cadastrarDisponibilidade(disponibilidadeParaCadastrar)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .jsonPath()
                    .getList("", DisponibilidadeResponseModel.class);
    }

    @Test
    @DisplayName("Cenário 1: Deve deletar disponibilidade com sucesso")
    @Tag("Functional")
    public void testDeveDeletarDisponibilidadeComSucesso(){
        disponibilidadeClient.deletarDisponibilidade(String.valueOf(disponibilidadeCadastrada.get(0).getIdDisponibilidade()), true)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @DisplayName("Cenário 2: Tentar deletar disponibilidade sem token")
    @Tag("Functional")
    public void testTentarDeletarDisponibilidadeSemToken(){
        disponibilidadeClient.deletarDisponibilidade(String.valueOf(disponibilidadeCadastrada.get(0).getIdDisponibilidade()), false)
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}