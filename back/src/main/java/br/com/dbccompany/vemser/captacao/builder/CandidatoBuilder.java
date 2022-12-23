package br.com.dbccompany.vemser.captacao.builder;

import br.com.dbccompany.vemser.captacao.dto.candidato.Ativo;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.EdicaoDTO;

import java.util.List;

public class CandidatoBuilder {

    public CandidatoCreateDTO criarCandidato() {
        return CandidatoCreateDTO.builder()
                .nome("Teste QA")
                .dataNascimento("1993-10-19")
                .email("teste.qa@gmail.com")
                .telefone("71 99999-9999")
                .rg("01.234.567-89")
                .cpf("012.345.678-90")
                .estado("BA")
                .cidade("Salvador")
                .ativo(Ativo.T.toString())
                .linguagens(List.of())
                .edicao(new EdicaoDTO())
                .formulario(0)
                .pcdboolean(false)
                .build();
    }

}
