package br.com.dbccompany.vemser.captacao.builder;

import br.com.dbccompany.vemser.captacao.dto.TipoValidacao;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoCreateDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.CandidatoNotaDTO;
import br.com.dbccompany.vemser.captacao.dto.candidato.EdicaoDTO;
import org.apache.commons.lang3.StringUtils;

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
                .ativo(TipoValidacao.T.toString())
                .linguagens(List.of())
                .edicao(new EdicaoDTO("1ª Edição"))
                .formulario(0)
                .pcdboolean(false)
                .build();
    }

    public CandidatoCreateDTO criarCandidatoSemPreencherCamposObrigatorios(){
        CandidatoCreateDTO candidatoInvalido = criarCandidato();
        candidatoInvalido.setNome(StringUtils.EMPTY);
        candidatoInvalido.setDataNascimento(StringUtils.EMPTY);
        candidatoInvalido.setTelefone(StringUtils.EMPTY);
        candidatoInvalido.setRg(StringUtils.EMPTY);
        candidatoInvalido.setEstado(StringUtils.EMPTY);
        candidatoInvalido.setCidade(StringUtils.EMPTY);

        return candidatoInvalido;
    }

    public CandidatoCreateDTO atualizarCandidato(){
        CandidatoCreateDTO candidatoAtualizado = criarCandidato();
        candidatoAtualizado.setNome("Atualizado");
        candidatoAtualizado.setTelefone("51 00000-0000");
        candidatoAtualizado.setRg("00.000.000-00");
        candidatoAtualizado.setEstado("Atualizado");
        candidatoAtualizado.setCidade("Atualizado");
        candidatoAtualizado.setPcdboolean(true);

        return candidatoAtualizado;
    }

}
