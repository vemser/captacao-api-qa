package br.com.dbccompany.vemser.tests.disponibilidade;

import client.DisponibilidadeClient;
import factory.DisponibilidadeDataFactory;
import models.disponibilidade.DisponibilidadeModel;
import models.disponibilidade.DisponibilidadeResponseModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Endpoint de deletar disponibilidade")
public class DeletarDisponibilidadeTest {
    private static final DisponibilidadeClient disponibilidadeClient = new DisponibilidadeClient();
    private static List<DisponibilidadeResponseModel> disponibilidadeCadastrada;
    private static DisponibilidadeModel disponibilidadeParaCadastrar;
    public Integer ID_GESTOR = 2;

    @BeforeEach
    public void criarMassaDados(){

        disponibilidadeParaCadastrar = DisponibilidadeDataFactory.disponibilidadeValida(ID_GESTOR);
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

        disponibilidadeClient.deletarDisponibilidade(disponibilidadeCadastrada.get(0).getIdDisponibilidade())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);

    }

    @Test
    @DisplayName("Cenário 2: Tentar deletar disponibilidade sem token")
    @Tag("Functional")
    public void testTentarDeletarDisponibilidadeSemToken(){

        disponibilidadeClient.deletarDisponibilidadeSemAutenticacao(disponibilidadeCadastrada.get(0).getIdDisponibilidade())
                .then()
                    .statusCode(HttpStatus.SC_FORBIDDEN);

        disponibilidadeClient.deletarDisponibilidade(disponibilidadeCadastrada.get(0).getIdDisponibilidade());

    }
}