package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.repository;

import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivrosRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByIdiomaContainingIgnoreCase(String listaLivrosIdioma);

    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();

    @Query("SELECT l from Livro l WHERE l.numeroDownloads >= 1000")
    List<Livro> filtrarLivrosComMilDownloads();


}
