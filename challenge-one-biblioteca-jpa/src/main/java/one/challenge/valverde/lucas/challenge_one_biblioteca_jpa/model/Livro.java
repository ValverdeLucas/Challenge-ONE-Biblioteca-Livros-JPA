package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    private String nomeAutor;
    private String idioma;
    private Double numeroDownloads;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    private Autor autor;

    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.resultado().get(0).titulo();
        this.nomeAutor = dadosLivro.resultado().get(0).autores().get(0).nome();
        this.idioma = dadosLivro.resultado().get(0).idiomas().get(0);
        this.numeroDownloads = dadosLivro.resultado().get(0).numeroDownloads();
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "\n************ Livro ************" +
                "\nTítulo: " + titulo +
                "\nAutor: " + nomeAutor +
                "\nIdioma: " + idioma +
                "\nNúmero de Downloads: " + numeroDownloads +
                "\n*******************************";
    }
}
