package factory.prova;

import models.edicao.EdicaoModel;
import models.prova.ProvaCriacaoModel;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.*;

public class ProvaDataFactory {

    private static final Random RANDOM = new Random();
    private static final Faker faker = new Faker(new Locale("pt-BR"));

    // Construtor privado para impedir a instânciação da classe
    private ProvaDataFactory() {
    }

    // Método para criar uma prova válida com questões fixas
    public static ProvaCriacaoModel provaValida() {
        // Cria uma prova base com informações comuns
        ProvaCriacaoModel prova = criarProvaBase();
        // Gera uma lista de questões fixas e define como as questões da prova
        List<Integer> idQuestoes = gerarListaDeQuestoesFixas();
        prova.setIdQuestoes(idQuestoes);
        return prova;
    }

    // Método para criar uma prova com questões aleatórias
    public static ProvaCriacaoModel provaComQuestoesAleatorias() {
        // Cria uma prova base com informações comuns
        ProvaCriacaoModel prova = criarProvaBase();
        // Recupera todas as questões disponíveis
        List<Integer> todasAsQuestoesDisponiveis = recuperarTodasAsQuestoesDisponiveis();
        // Seleciona aleatoriamente um subconjunto de questões e define como as questões da prova
        List<Integer> questoesSelecionadas = selecionarQuestoesAleatorias(todasAsQuestoesDisponiveis);
        prova.setIdQuestoes(questoesSelecionadas);
        return prova;
    }

    // Método para criar uma prova com mais de 10 questões
    public static ProvaCriacaoModel criarProvaComMaisDe10Questoes(int numeroQuestoesDesejadas) {
        // Cria uma prova base com informações comuns
        ProvaCriacaoModel prova = criarProvaBase();
        // Recupera todas as questões disponíveis
        List<Integer> todasAsQuestoesDisponiveis = recuperarTodasAsQuestoesDisponiveis();
        // Seleciona aleatoriamente um subconjunto de questões com o tamanho especificado
        List<Integer> questoesSelecionadas = selecionarQuestoesAleatorias(todasAsQuestoesDisponiveis, numeroQuestoesDesejadas);
        prova.setIdQuestoes(questoesSelecionadas);
        return prova;
    }

    // Método para criar uma prova base com informações comuns
    private static ProvaCriacaoModel criarProvaBase() {
        // Gera datas de início e final aleatórias
        LocalDateTime dataInicio = gerarDataAleatoria();
        LocalDateTime dataFinal = gerarDataAleatoria();
        // Cria uma nova prova com as informações comuns
        ProvaCriacaoModel prova = new ProvaCriacaoModel();
        prova.setDataInicio(dataInicio);
        prova.setDataFinal(dataFinal);
        prova.setVersaoProva(RANDOM.nextInt());
        prova.setTituloProva(faker.book().title());
        prova.setEnunciadoProva(faker.lorem().sentence());
        // Define a edição da prova
        EdicaoModel edicaoModel = new EdicaoModel();
        edicaoModel.setIdEdicao(1);
        prova.setIdEdicao(edicaoModel.getIdEdicao());
        // Gera uma lista de linguagens e define como as linguagens da prova
        List<Integer> linguagens = gerarListaDeLinguagens();
        prova.setIdsLinguagens(linguagens);
        return prova;
    }

    // Método para gerar uma data aleatória no futuro
    private static LocalDateTime gerarDataAleatoria() {
        int diasNoFuturo = RANDOM.nextInt(1000);
        return LocalDateTime.now().plusDays(diasNoFuturo).withHour(15).withMinute(47).withSecond(2).withNano(602000000);
    }

    // Método para gerar uma lista de códigos de linguagens já cadastradas no banco
    private static List<Integer> gerarListaDeLinguagens() {
        List<Integer> linguagens = new ArrayList<>();
        // Adiciona códigos de linguagens à lista
        Collections.addAll(linguagens, 22, 23, 24, 37);
        return linguagens;
    }

    // Método para gerar uma lista de questões fixas
    private static List<Integer> gerarListaDeQuestoesFixas() {
        List<Integer> idQuestoes = new ArrayList<>();
        // Adiciona números de questões à lista
        for (int i = 1; i <= 10; i++) {
            idQuestoes.add(i);
        }
        return idQuestoes;
    }

    // Método para recuperar todas as questões disponíveis
    private static List<Integer> recuperarTodasAsQuestoesDisponiveis() {
        List<Integer> todasAsQuestoes = new ArrayList<>();
        // Adiciona números de questões à lista
        for (int i = 1; i <= 100; i++) {
            todasAsQuestoes.add(i);
        }
        return todasAsQuestoes;
    }

    // Método para selecionar aleatoriamente um subconjunto de questões
    private static List<Integer> selecionarQuestoesAleatorias(List<Integer> todasAsQuestoes) {
        int numeroQuestoesDesejadas = 10;
        List<Integer> questoesSelecionadas = new ArrayList<>(todasAsQuestoes);
        // Embaralha a lista de questões
        Collections.shuffle(questoesSelecionadas);
        // Seleciona um subconjunto das questões embaralhadas
        return questoesSelecionadas.subList(0, Math.min(numeroQuestoesDesejadas, todasAsQuestoes.size()));
    }

    // Método para selecionar aleatoriamente um subconjunto de questões com o tamanho especificado
    private static List<Integer> selecionarQuestoesAleatorias(List<Integer> todasAsQuestoes, int numeroQuestoesDesejadas) {
        List<Integer> questoesSelecionadas = new ArrayList<>(todasAsQuestoes);
        // Embaralha a lista de questões
        Collections.shuffle(questoesSelecionadas);
        // Seleciona um subconjunto das questões embaralhadas com o tamanho desejado
        return questoesSelecionadas.subList(0, Math.min(numeroQuestoesDesejadas, todasAsQuestoes.size()));
    }

    // Método para criar uma prova com título vazio
    public static ProvaCriacaoModel provaComTituloVazio() {
        // Cria uma prova base com informações comuns
        ProvaCriacaoModel prova = provaValida();
        // Define o título da prova como vazio
        prova.setTituloProva("");

        return prova;
    }

    // Método para criar uma prova com Título com mais de 100 caracteres
    public static ProvaCriacaoModel provaComTituloExcedendoOs100Caracteres() {
        // Cria uma prova base com informações comuns
        ProvaCriacaoModel prova = provaValida();
        // Define o título da prova com mais de 100 caracteres
        prova.setTituloProva(faker.lorem().characters(101));

        return prova;
    }

    public static ProvaCriacaoModel criarProvaSemSelecionarQuestoes() {
        // Cria uma prova base com informações comuns
        ProvaCriacaoModel prova = criarProvaBase();

        return prova;
    }
}