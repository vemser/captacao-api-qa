package models.candidato;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JSONListaCandidatoResponse {

    public Integer totalElementos;
    public Integer quantidadePaginas;
    public Integer pagina;
    public Integer tamanho;
    public List<CandidatoModel> elementos;
}

