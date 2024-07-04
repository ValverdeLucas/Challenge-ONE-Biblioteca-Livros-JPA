package one.challenge.valverde.lucas.challenge_one_biblioteca_jpa.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}

