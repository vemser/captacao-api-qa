package factory.candidatoprova;

import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.linguagem.LinguagemClient;
import client.prova.ProvaClient;
import client.trilha.TrilhaClient;
import factory.candidato.CandidatoDataFactory;
import factory.formulario.FormularioDataFactory;
import factory.prova.ProvaDataFactory;
import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoModel;
import models.candidatoprova.CandidatoProvaModel;
import models.edicao.EdicaoModel;
import models.formulario.FormularioCriacaoModel;
import models.formulario.FormularioCriacaoResponseModel;
import models.linguagem.LinguagemModel;
import models.prova.ProvaCriacaoModel;
import models.prova.ProvaResponse;
import models.trilha.TrilhaModel;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CandidatoProvaDataFactory {

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    private static final TrilhaClient trilhaClient = new TrilhaClient();
    private static final FormularioClient formularioClient = new FormularioClient();
    private static final EdicaoClient edicaoClient = new EdicaoClient();
    private static final LinguagemClient linguagemClient = new LinguagemClient();
    private static final CandidatoClient candidatoClient = new CandidatoClient();
    private static final ProvaClient provaClient = new ProvaClient();

    public CandidatoProvaModel novoCandidatoProva() {
        return criarCandidatoProva();
    }

    private static CandidatoProvaModel criarCandidatoProva() {
        List<String> listaDeNomeDeTrilhas = new ArrayList<>();
        List<TrilhaModel> listaDeTrilhas = Arrays.stream(trilhaClient.listarTodasAsTrilhas()
                    .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .as(TrilhaModel[].class))
                        .toList()
                    ;

        listaDeNomeDeTrilhas.add(listaDeTrilhas.get(0).getNome());

        FormularioCriacaoModel formulario = FormularioDataFactory.formularioValido(listaDeNomeDeTrilhas);

        FormularioCriacaoResponseModel formularioCriado = formularioClient.criarFormularioComFormularioEntity(formulario);

        EdicaoModel edicaoCriada = edicaoClient.criarEdicao();
        LinguagemModel linguagemCriada = linguagemClient.retornarPrimeiraLinguagemCadastrada();

        CandidatoCriacaoModel candidatoCriado = CandidatoDataFactory.candidatoCriacaoValido(edicaoCriada, formularioCriado.getIdFormulario(), linguagemCriada.getNome());

        CandidatoModel candidatoCadastrado = candidatoClient.cadastrarCandidatoComCandidatoEntity(candidatoCriado)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(CandidatoModel.class);

        ProvaCriacaoModel prova = ProvaDataFactory.provaValida();

        ProvaResponse provaCriada = provaClient.criarProva(prova)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(ProvaResponse.class);

        CandidatoProvaModel candidatoProvaModel = new CandidatoProvaModel();
        candidatoProvaModel.setIdCandidato(candidatoCadastrado.getIdCandidato());
        candidatoProvaModel.setIdProva(provaCriada.getId());
        candidatoProvaModel.setDataInicio("2024-03-20T09:12:28Z");
        candidatoProvaModel.setDataFim("2024-03-20T09:12:28Z");

        return candidatoProvaModel;

    }

}
