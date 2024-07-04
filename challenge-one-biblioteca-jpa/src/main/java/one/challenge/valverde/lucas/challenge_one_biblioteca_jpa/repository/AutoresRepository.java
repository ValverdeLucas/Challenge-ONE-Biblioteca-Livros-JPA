package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.repository;

import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
}
