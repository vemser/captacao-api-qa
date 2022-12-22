package br.com.dbccompany.vemser.captacao.builder;

import br.com.dbccompany.vemser.captacao.dto.candidato.Ativo;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.EdicaoDTO;

import java.util.List;

public class CandidatoBuilder {

    public CandidatoCreateDTO criarCandidato() {
        return CandidatoCreateDTO.builder()
                .nome("")
                .dataNascimento("")
                .email("")
                .telefone("")
                .rg("")
                .cpf("")
                .estado("")
                .cidade("")
                .ativo(Ativo.T.toString())
                .linguagens(List.of())
                .edicao(new EdicaoDTO())
                .formulario(0)
                .pcdboolean(false)
                .build();
    }

}
