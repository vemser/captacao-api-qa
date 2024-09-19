package factory.candidato;

import factory.edicao.EdicaoDataFactory;
import factory.formulario.FormularioDataFactory;
import models.candidato.CandidatoCriacaoModel;
import models.edicao.EdicaoModel;
import net.datafaker.Faker;
import utils.auth.Email;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static utils.config.Tools.removerCaracteresEspeciais;

public class CandidatoDataFactory {

    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static CandidatoCriacaoModel candidatoCriacaoValido(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComNomeNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setNome(null);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComNomeEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emptyString = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setNome(emptyString);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComDataNascimentoNoFuturo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoFuturo = "2195-03-01";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoFuturo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComMenosDeDezesseisAnos(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoMenosDeDezesseisAnos = "2022-03-01";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoMenosDeDezesseisAnos);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComDataDeNascimentoNula(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoNula = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoNula);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComDataDeNascimentoEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComDataDeNascimentoInvalida(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String dataNascimentoInvalida = "dataInvalida";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setDataNascimento(dataNascimentoInvalida);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoCriacaoValidoComEmailEspecifico(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem, String email) {

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEmail(email);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emailNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEmail(emailNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emailEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEmail(emailEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailSemDominio(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emailSemDominio = "email";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEmail(emailSemDominio);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailInvalido(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String emailInvalido = "a30298ad()(*&";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEmail(emailInvalido);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEmailJaCadastrado() {
        CandidatoCriacaoModel candidato = novoCandidato();

        candidato.setEmail("igor.henriques@live.com");

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComTelefoneNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String telefoneNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setTelefone(telefoneNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComTelefoneEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String telefoneEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setTelefone(telefoneEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComRgNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String rgNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setRg(rgNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComRgEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String rgEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setRg(rgEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComRgMaiorQueTrinta(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String rgMaiorQueTrintaCaracteres = "123456789123456789123456789123456789";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setRg(rgMaiorQueTrintaCaracteres);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCpfNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cpfNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setCpf(cpfNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCpfEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cpfEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setCpf(cpfEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCpfInvalido(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cpfInvalido = faker.cpf().invalid();

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setCpf(cpfInvalido);

        return candidato;
    }



    public static CandidatoCriacaoModel candidatoComCpfJaCadastrado() {
        CandidatoCriacaoModel candidato = novoCandidato();

        candidato.setEmail(faker.internet().emailAddress());
        candidato.setCpf("06291661535");

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEstadoNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String estadoNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEstado(estadoNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEstadoEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String estadoEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEstado(estadoEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCidadeNula(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cidadeNula = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setCidade(cidadeNula);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComCidadeEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String cidadeEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setCidade(cidadeEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComPcdNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String pcdNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setPcd(pcdNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComPcdEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String pcdEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setPcd(pcdEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComAtivoNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String ativoNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setAtivo(ativoNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComAtivoEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String ativoEmBranco = "";

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setAtivo(ativoEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComListaNulaDeLinguagem(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        List<String> listaNulaLinguagem = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setLinguagens(listaNulaLinguagem);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComListaDeLinguagemEmBranco(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String linguagemEmBranco = "";
        List<String> listaLinguagemEmBranco = Collections.singletonList(linguagemEmBranco);

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setLinguagens(listaLinguagemEmBranco);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComListaDeLinguagemNaoCadastrada(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        String linguagemNaoCadastrada = "linguagemNaoCadastrada";
        List<String> listaLinguagemNaoCadastrada = Collections.singletonList(linguagemNaoCadastrada);

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setLinguagens(listaLinguagemNaoCadastrada);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEdicaoNula(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        EdicaoModel edicaoNula = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEdicao(edicaoNula);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComEdicaoNaoExistente(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        EdicaoModel edicaoNaoExistente = EdicaoDataFactory.edicaoValida();
        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setEdicao(edicaoNaoExistente);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComIdFormularioNulo(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        Integer idFormularioNulo = null;

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

        candidato.setFormulario(idFormularioNulo);

        return candidato;
    }

    public static CandidatoCriacaoModel candidatoComIdFormularioNaoCadastrado(EdicaoModel edicao, Integer idFormulario, String nomeLinguagem) {
        Integer idFormularioNaoCadastrado = FormularioDataFactory.idFormularioNaoCadastrado();

        CandidatoCriacaoModel candidato = novoCandidato();
        candidato.setEdicao(edicao);
        candidato.setFormulario(idFormulario);
        candidato.setLinguagens(Collections.singletonList(nomeLinguagem));

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
        String pcd = "Não possui";
        String ativo = "T";

        String linguagem = "Java";
        List<String> linguagens = new ArrayList<>();
        linguagens.add(linguagem);

        Integer idEdicao = faker.random().nextInt(100,10000);
        String nomeEdicao = "VEMSER_"+faker.random().nextInt(100,10000);
        Integer notaCorte = faker.random().nextInt(0, 100);
        EdicaoModel edicao = new EdicaoModel(idEdicao, nomeEdicao, notaCorte);

        Integer idFormulario = 1;

        CandidatoCriacaoModel candidato = new CandidatoCriacaoModel();
        candidato.setNome(removerCaracteresEspeciais(faker.name().fullName()));
        candidato.setDataNascimento(dateFormat.format(faker.date().birthday()));
        candidato.setEmail(faker.internet().emailAddress());
        candidato.setTelefone(faker.phoneNumber().cellPhone());
        candidato.setRg(faker.idNumber().valid());
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
