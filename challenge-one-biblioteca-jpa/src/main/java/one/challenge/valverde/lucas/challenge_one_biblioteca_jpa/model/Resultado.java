package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Resultado(@JsonAlias("title") String titulo,
                        @JsonAlias("authors") List<DadosAutor> autores,
                        @JsonAlias("languages") List<String> idiomas,
                        @JsonAlias("download_count") Double numeroDownloads) {
}
