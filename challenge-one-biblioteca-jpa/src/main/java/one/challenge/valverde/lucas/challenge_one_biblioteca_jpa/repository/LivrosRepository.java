package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.repository;

import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model.Autor;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivrosRepository extends JpaRepository<Livro, Long> {
//    List<Livro> findByTituloContainingIgnoreCase(String tituloLivro);
//    List<Autor> findByAutor();
}
