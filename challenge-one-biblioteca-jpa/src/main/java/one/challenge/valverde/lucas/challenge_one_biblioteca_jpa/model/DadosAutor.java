package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosAutor(@JsonAlias("birth_year") Integer anoNascimento,
                         @JsonAlias("death_year") Integer anoMorte,
                         @JsonAlias("name") String nome) {
}
