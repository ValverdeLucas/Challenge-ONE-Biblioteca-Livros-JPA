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
    private LivrosRepository repositorioLivro;
    private AutoresRepository repositorioAutor;
    private List<Livro> listaLivros = new ArrayList<>();
    private List<Autor> listaAutores = new ArrayList<>();
    private Optional<Livro> livroBusca;
    private Optional<Autor> autorBusca;
    private Autor autorBuscado;


    public static int validarOpcao(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.println("Por favor, insira um valor númerico válido:");
            input.next();
        }
        return input.nextInt();
    }

    public Main(LivrosRepository repositorioLivro, AutoresRepository repositorioAutor) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
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
                    6- Listar top 10 livros mais baixados
                    7- Buscar autor específico
                                        
                    9- Mostrar estatísticas do Banco de Dados
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
                case 6:
                    listarTop10LivrosBaixados();
                    break;
                case 7:
                    buscarAutorEspecificoNoDB();
                    break;
                case 9:
                    mostrarEstatisticasDB();
                    break;
                case 0:
                    System.out.println("***** Encerrando aplicação! *****");
                default:
                    System.out.println("Opção inválida!");
            }

        }
    }


    private void buscarLivro() {
        DadosLivro dados = getBuscarLivro();

        if (!dados.resultado().isEmpty()) {
            Livro livro = new Livro(dados);
            Autor autor = new Autor(dados.resultado().getFirst().autores().getFirst());

            livroBusca = repositorioLivro.findByTituloContainingIgnoreCase(livro.getTitulo());
            autorBusca = repositorioAutor.findByNomeContainingIgnoreCase(autor.getNome());

            if (autorBusca.isEmpty()) {
                repositorioAutor.save(autor);
                adicionarLivro(autor, livro);

            } else {
                adicionarLivro(autor, livro);

            }
            System.out.println(livro);

        } else {
            System.out.println("Título não encontrado!");
        }
    }

    private void adicionarLivro(Autor autor, Livro livro) {

        livroBusca = repositorioLivro.findByTituloContainingIgnoreCase(livro.getTitulo());
        autorBusca = repositorioAutor.findByNomeContainingIgnoreCase(autor.getNome());

        if (autorBusca.isPresent()) {

            autorBuscado = autorBusca.get();

            if (livroBusca.isPresent()) {
                System.out.println("O livro já está registrado!");

            } else {
                autorBuscado.setLivros(livro);
                repositorioLivro.save(livro);
            }

        }
    }

    private DadosLivro getBuscarLivro() {
        System.out.println("Digite o nome do Livro que deseja buscar:");
        var livro = input.nextLine();
        var json = requisicaoAPI.obterDados(ENDERECO + livro.replace(" ", "%20").toLowerCase());
        return conversor.obterDados(json, DadosLivro.class);
    }

    private void listarLivrosNoBanco() {
        listaLivros = repositorioLivro.findAll();
        listaLivros.forEach(System.out::println);
    }

    private void listarAutoresNoBanco() {
        System.out.println("_____ Autores registrados no banco _____");
        listaAutores = repositorioAutor.findAll();
        listaAutores.forEach(System.out::println);
    }

    private void listarAutoresPorAno() {
        System.out.println("Digite o ano o qual deseja consultar os autores vivos:");
        var listaAutoresAno = input.nextInt();
        input.nextLine();

        listaAutores = repositorioAutor.encontrarAutorPorAno(listaAutoresAno);
        System.out.println("_____ Autores vivos em " + listaAutoresAno + " _____");
        listaAutores.forEach(System.out::println);

    }

    private void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma o qual deseja realizar a busca:");
        System.out.println("""
                PT - Português
                EN - Inglês
                ES - Espanhol
                """);
        var listaLivrosIdioma = input.nextLine();

        switch (listaLivrosIdioma) {
            case "pt":
                listaLivros = repositorioLivro.findByIdiomaContainingIgnoreCase(listaLivrosIdioma);
                System.out.println("_____ Livros em PT - Português | Quantidade: " + listaLivros.size() + " _____");
                listaLivros.forEach(System.out::println);
                break;
            case "en":
                listaLivros = repositorioLivro.findByIdiomaContainingIgnoreCase(listaLivrosIdioma);
                System.out.println("_____ Livros em EN - Inglês | Quantidade: " + listaLivros.size() + " _____");
                listaLivros.forEach(System.out::println);
                break;
            case "es":
                listaLivros = repositorioLivro.findByIdiomaContainingIgnoreCase(listaLivrosIdioma);
                System.out.println("_____ Livros em ES - Espanhol | Quantidade: " + listaLivros.size() + " _____");
                listaLivros.forEach(System.out::println);
                break;
            default:
                System.out.println("Idioma não permitido!");
        }
    }

    private void listarTop10LivrosBaixados() {
        System.out.println("_____ Top 10 livros mais baixados _____");
        listaLivros = repositorioLivro.findTop10ByOrderByNumeroDownloadsDesc();
        listaLivros.forEach(System.out::println);
    }

    private void mostrarEstatisticasDB() {

        listaAutores = repositorioAutor.findAll();
        listaLivros = repositorioLivro.findAll();

        List<Livro> livrosPortugues = repositorioLivro.findByIdiomaContainingIgnoreCase("pt");
        List<Livro> livrosIngles = repositorioLivro.findByIdiomaContainingIgnoreCase("en");
        List<Livro> livrosEspanhol = repositorioLivro.findByIdiomaContainingIgnoreCase("es");
        List<Livro> livrosComMaisDeMilDownloads = repositorioLivro.filtrarLivrosComMilDownloads();

        System.out.println(String.format("""
                ***** Informações do Banco *****
                Total de autores cadastrados no Banco: %d
                Total de livros cadastrados no Banco: %d
                Total de livros cadastrados (por línguas específicas):
                - Português (PT) - %d
                - Inglês (EN) - %d
                - Espanhol (ES) - %d
                Total de livros com mais de 1000 downloads: %d
                """, listaAutores.size(), listaLivros.size(), livrosPortugues.size(), livrosIngles.size(), livrosEspanhol.size(), livrosComMaisDeMilDownloads.size()));
    }

    private void buscarAutorEspecificoNoDB() {
        System.out.println("Digite o nome do autor que deseja buscar (apenas o nome ou sobrenome)");
        var autor = input.nextLine();
        autorBusca = repositorioAutor.findByNomeContainingIgnoreCase(autor);
        if (autorBusca.isPresent()) {
            autorBuscado = autorBusca.get();
            System.out.println(autorBuscado);
        } else {
            System.out.println("Autor não localizado!");
        }
    }


}

