package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.repository;

import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a from Autor a WHERE :ano BETWEEN a.anoNascimento AND a.anoMorte")
    List<Autor> encontrarAutorPorAno(Integer ano);

}
