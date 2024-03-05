package utils.config;

import models.candidato.CandidatoCriacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static CandidatoCriacaoModel candidatoResponseParaCandidatoCriacao(CandidatoCriacaoResponseModel candidatoResponse) {

        CandidatoCriacaoModel candidatoCriacao = new CandidatoCriacaoModel();
        candidatoCriacao.setNome(candidatoResponse.getNome());
        candidatoCriacao.setDataNascimento(candidatoResponse.getDataNascimento());
        candidatoCriacao.setEmail(candidatoResponse.getEmail());
        candidatoCriacao.setTelefone(candidatoResponse.getTelefone());
        candidatoCriacao.setRg(candidatoResponse.getRg());
        candidatoCriacao.setCpf(candidatoResponse.getCpf());
        candidatoCriacao.setEstado(candidatoResponse.getEstado());
        candidatoCriacao.setCidade(candidatoResponse.getCidade());
        candidatoCriacao.setPcd(candidatoResponse.getPcd());
        candidatoCriacao.setAtivo(candidatoResponse.getAtivo());

        List<String> listaDeLinguagens = new ArrayList<>();
        candidatoResponse.getLinguagens().stream().forEach(linguagemModel -> listaDeLinguagens.add(linguagemModel.getNome()));
        candidatoCriacao.setLinguagens(listaDeLinguagens);

        candidatoCriacao.setEdicao(candidatoResponse.getEdicao());
        candidatoCriacao.setFormulario(candidatoResponse.getFormulario().getIdFormulario());

        return candidatoCriacao;
    }
}
