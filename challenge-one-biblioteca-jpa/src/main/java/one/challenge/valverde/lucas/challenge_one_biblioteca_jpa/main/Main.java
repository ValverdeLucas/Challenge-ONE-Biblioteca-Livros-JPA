package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.main;

import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model.Autor;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model.DadosLivro;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model.Livro;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.repository.AutoresRepository;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.repository.LivrosRepository;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.service.ConverteDados;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.service.RequisicaoAPI;

import java.util.*;

public class Main {

    private Scanner input = new Scanner(System.in);
    private RequisicaoAPI requisicaoAPI = new RequisicaoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private LivrosRepository repositorio;
    private List<Livro> listaLivros = new ArrayList<>();
    private Optional<Livro> livroBusca;


    public static int validarOpcao(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.println("Por favor, insira um valor númerico válido:");
            input.next();
        }
        return input.nextInt();
    }

    public Main(LivrosRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {

        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    1- Buscar livro pelo título
                    2- Listar livros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos em um determinado ano
                    5- Listar livros em um determinado idioma
                                        
                    0- Sair
                    """;
            System.out.println(menu);
            opcao = validarOpcao(input);
            input.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivrosNoBanco();
                    break;
                case 3:
                    listarAutoresNoBanco();
                    break;
                case 4:
                    listarAutoresPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        }
    }

    private void buscarLivro() {
        DadosLivro dados = getBuscarLivro();
        Livro livro = new Livro(dados);
        repositorio.save(livro);
        System.out.println(livro);
    }

    private DadosLivro getBuscarLivro() {
        System.out.println("Digite o nome do Livro que deseja buscar:");
        var livro = input.nextLine();
        var json = requisicaoAPI.obterDados(ENDERECO + livro.replace(" ", "%20").toLowerCase());
        return conversor.obterDados(json, DadosLivro.class);
    }

    private void listarLivrosNoBanco() {
        listaLivros = repositorio.findAll();
        listaLivros.forEach(System.out::println);
    }

    private void listarAutoresNoBanco() {
    }

    private void listarAutoresPorAno() {
        System.out.println("Digite o ano o qual deseja consultar os autores vivos:");
        var listaAutoresAno = input.nextLine();
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma o qual deseja realizar a busca:");
        System.out.println("""
                PT - Português
                EN - Inglês
                ES - Espanhol
                FR - Francês
                AL - Alemão
                JP - Japonês
                """);
        var listaLivrosIdioma = input.nextLine();
    }


}

