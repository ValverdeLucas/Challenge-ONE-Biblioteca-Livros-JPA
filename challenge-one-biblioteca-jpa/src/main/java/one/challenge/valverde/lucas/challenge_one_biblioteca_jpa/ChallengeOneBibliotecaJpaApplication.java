package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa;

import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.main.Main;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.repository.AutoresRepository;
import one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeOneBibliotecaJpaApplication implements CommandLineRunner {

    @Autowired
    private LivrosRepository repositorio;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeOneBibliotecaJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main(repositorio);
        main.exibeMenu();
    }
}