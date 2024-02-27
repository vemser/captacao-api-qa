package factory;

import models.candidato.CandidatoCriacaoModel;
import models.candidato.JSONListaCandidatoResponse;
import models.edicao.EdicaoModel;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import client.CandidatoClient;
import client.FormularioClient;
import utils.Email;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static utils.Tools.removerCaracteresEspeciais;

public class CandidatoDataFactory {

    private static Faker faker = new Faker(new Locale("pt-BR"));
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static FormularioClient formularioClient = new FormularioClient();
    private static FormularioDataFactory formularioDataFactory = new FormularioDataFactory();
    private static CandidatoClient candidatoClient = new CandidatoClient();
    private static EdicaoDataFactory edicaoDataFactory = new EdicaoDataFactory();


    public static CandidatoCriacaoModel candidatoCriacaoValido(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComNomeNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setNome(null);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComNomeEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emptyString = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setNome(emptyString);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComDataNascimentoNoFuturo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoFuturo = "2195-03-01";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoFuturo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComMenosDeDezesseisAnos(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoMenosDeDezesseisAnos = "2022-03-01";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoMenosDeDezesseisAnos);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComDataDeNascimentoNula(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoNula = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoNula);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComDataDeNascimentoEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComDataDeNascimentoInvalida(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoInvalida = "dataInvalida";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoInvalida);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoCriacaoValidoComEmailEspecifico(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem, String email) {

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEmail(email);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emailNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEmail(emailNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emailEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEmail(emailEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailSemDominio(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emailSemDominio = "email";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEmail(emailSemDominio);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailInvalido(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emailInvalido = "a30298ad()(*&";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEmail(emailInvalido);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailJaCadastrado(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        JSONListaCandidatoResponse listaCandidatoResponse = candidatoClient.listarNumCandidatos(1)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(JSONListaCandidatoResponse.class);

        String emailCadastrado = listaCandidatoResponse.getElementos().get(0).getEmail();
        EdicaoModel edicaoCadastrada = listaCandidatoResponse.getElementos().get(0).getEdicao();

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEmail(emailCadastrado);
        candidato.setEdicao(edicaoCadastrada);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComTelefoneNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String telefoneNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setTelefone(telefoneNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComTelefoneEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String telefoneEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setTelefone(telefoneEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComTelefoneInvalido(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String telefoneInvalido = "f02f9ad)(*_(*";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setTelefone(telefoneInvalido);

        return candidato;
    }
    public static CandidatoCriacaoModel candidatoComRgNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String rgNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setRg(rgNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComRgEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String rgEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setRg(rgEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComRgMaiorQueTrinta(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String rgMaiorQueTrintaCaracteres = "123456789123456789123456789123456789";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setRg(rgMaiorQueTrintaCaracteres);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCpfNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cpfNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setCpf(cpfNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCpfEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cpfEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setCpf(cpfEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCpfInvalido(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cpfInvalido = faker.cpf().invalid();

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setCpf(cpfInvalido);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCpfJaCadastrado(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        JSONListaCandidatoResponse listaCandidatoResponse = candidatoClient.listarNumCandidatos(1)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(JSONListaCandidatoResponse.class);

        String cpfCadastrado = listaCandidatoResponse.getElementos().get(0).getCpf();
        EdicaoModel edicaoCadastrada = listaCandidatoResponse.getElementos().get(0).getEdicao();

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setCpf(cpfCadastrado);
        candidato.setEdicao(edicaoCadastrada);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEstadoNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String estadoNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEstado(estadoNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEstadoEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String estadoEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEstado(estadoEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCidadeNula(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cidadeNula = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setCidade(cidadeNula);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCidadeEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cidadeEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setCidade(cidadeEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComPcdNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String pcdNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setPcd(pcdNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComPcdEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String pcdEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setPcd(pcdEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComAtivoNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String ativoNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setAtivo(ativoNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComAtivoEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String ativoEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setAtivo(ativoEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComListaContendoLinguagemNula(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String linguagemNula = null;
        List<String> listaLinguagemNula = Arrays.asList(linguagemNula);

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setLinguagens(listaLinguagemNula);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComListaNulaDeLinguagem(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        List<String> listaNulaLinguagem = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setLinguagens(listaNulaLinguagem);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComListaDeLinguagemEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String linguagemEmBranco = "";
        List<String> listaLinguagemEmBranco = Arrays.asList(linguagemEmBranco);

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setLinguagens(listaLinguagemEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComListaDeLinguagemNaoCadastrada(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String linguagemNaoCadastrada = "linguagemNaoCadastrada";
        List<String> listaLinguagemNaoCadastrada = Arrays.asList(linguagemNaoCadastrada);

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setLinguagens(listaLinguagemNaoCadastrada);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEdicaoNula(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        EdicaoModel edicaoNula = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEdicao(edicaoNula);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEdicaoNaoExistente(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        EdicaoModel edicaoNaoExistente = edicaoDataFactory.edicaoValida();

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setEdicao(edicaoNaoExistente);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComIdFormularioNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        Integer idFormularioNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setFormulario(idFormularioNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComIdFormularioNaoCadastrado(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        Integer idFormularioNaoCadastrado = formularioDataFactory.idFormularioNaoCadastrado();

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Arrays.asList(nomeLinguagem));

        candidato.setFormulario(idFormularioNaoCadastrado);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComNovoEmail(CandidatoCriacaoModel candidato) {
        String novoEmail = Email.getEmail();

        candidato.setEmail(novoEmail);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComNovoNome(CandidatoCriacaoModel candidato) {
        String novoNome = "NOME SOBRENOME";

        candidato.setNome(novoNome);

        return candidato;
    }

    private static CandidatoCriacaoModel novoCandidato() {
        String rgValido = "11.027.125-7";
        String pcd = "Não possui";
        String ativo = "T";

        String linguagem = "Java";
        List<String> linguagens = new ArrayList<>();
        linguagens.add(linguagem);

        Integer idEdicao = 11;
        String nomeEdicao = "VEMSER_11";
        EdicaoModel edicao = new EdicaoModel(idEdicao, nomeEdicao);

        Integer idFormulario = 1;


        CandidatoCriacaoModel candidato = new CandidatoCriacaoModel();
        candidato.setNome(removerCaracteresEspeciais(faker.name().fullName()));
        candidato.setDataNascimento(dateFormat.format(faker.date().birthday()));
        candidato.setEmail(Email.getEmail());
        candidato.setTelefone(faker.phoneNumber().cellPhone());
        candidato.setRg(rgValido);
        candidato.setCpf(faker.cpf().valid());
        candidato.setEstado(faker.address().stateAbbr());
        candidato.setCidade(faker.address().city());
        candidato.setPcd(pcd);
        candidato.setAtivo(ativo);
        candidato.setLinguagens(linguagens);
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);

        return candidato;
    }
}
