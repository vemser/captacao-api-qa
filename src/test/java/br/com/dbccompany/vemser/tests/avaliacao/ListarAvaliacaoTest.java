package br.com.dbccompany.vemser.tests.avaliacao;

import client.avaliacao.AvaliacaoClient;
import client.candidato.CandidatoClient;
import client.edicao.EdicaoClient;
import client.formulario.FormularioClient;
import client.inscricao.InscricaoClient;
import factory.avaliacao.AvaliacaoDataFactory;
import models.avaliacao.AvaliacaoCriacaoModel;
import models.avaliacao.AvaliacaoModel;
import models.candidato.CandidatoCriacaoResponseModel;
import models.inscricao.InscricaoModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Endpoint de listagem de avaliação")
class ListarAvaliacaoTest{

	private static final CandidatoClient candidatoClient = new CandidatoClient();
	private static final InscricaoClient inscricaoClient = new InscricaoClient();
	private static final AvaliacaoClient avaliacaoClient = new AvaliacaoClient();
	private static final EdicaoClient edicaoClient = new EdicaoClient();
	private static final FormularioClient formularioClient = new FormularioClient();
	private static CandidatoCriacaoResponseModel candidatoCadastrado;
	private static InscricaoModel inscricaoCadastrada;
	private static AvaliacaoCriacaoModel avaliacao;
	private static AvaliacaoModel avaliacaoCadastrada;
	@BeforeAll
	public static void setUp(){
		candidatoCadastrado = candidatoClient.criarECadastrarCandidatoComCandidatoEntity()
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.extract()
				.as(CandidatoCriacaoResponseModel.class);
		inscricaoCadastrada = inscricaoClient.cadastrarInscricao(candidatoCadastrado.getIdCandidato())
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.extract()
				.as(InscricaoModel.class);
		avaliacao = AvaliacaoDataFactory.avaliacaoValida(inscricaoCadastrada.getIdInscricao(), true);

		avaliacaoCadastrada = avaliacaoClient.cadastrarAvaliacao(avaliacao)
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.extract()
				.as(AvaliacaoModel.class);
	}

	@AfterAll
	public static void setDown(){
		avaliacaoClient.deletarAvaliacao(avaliacaoCadastrada.getIdAvaliacao());
		edicaoClient.deletarEdicao(candidatoCadastrado.getEdicao().getIdEdicao());
		formularioClient.deletarFormulario(candidatoCadastrado.getFormulario().getIdFormulario());
		candidatoClient.deletarCandidato(candidatoCadastrado.getIdCandidato());
	}

	@Test
	@DisplayName("Cenário 1: Deve listar toda avaliação com sucesso")
	@Tag("Regression")
	void testListarTodaAvaliacaoComSucesso() {
		avaliacaoClient.listarTodaAvaliacao(true)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body("totalElementos", notNullValue())
				.body("elementos", notNullValue());
	}

	@Test
	@DisplayName("Cenário 2: Tentar listar toda avaliação sem autenticação")
	@Tag("Regression")
	void testTentarListarTodaAvaliacaoSemAutenticacao() {
		avaliacaoClient.listarTodaAvaliacao(false)
				.then()
				.statusCode(HttpStatus.SC_FORBIDDEN);
	}

	@Test
	@DisplayName("Cenário 3: Validar schema listar toda avaliação")
	public void testValidarSchemaListarTodaAvaliacao(){
		avaliacaoClient.listarTodaAvaliacao(true)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/avaliacao/Listar_toda_avaliacao.json"));

	}
}
