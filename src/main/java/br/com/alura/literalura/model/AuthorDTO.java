package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDTO(Long id,
                        String name,
                        @JsonAlias("birth_year") Integer yearOfBirth,
                        @JsonAlias("death_year") Integer yearOfDeath) {

    @Override
    public String toString() {
        return "Nome: " + name() +
                "\nData De Nascimento: " + (yearOfBirth() != null ? yearOfBirth() : "Desconhecido") +
                "\nData De Falecimento: " + (yearOfDeath() != null ? yearOfDeath() : "Desconhecido");
    }
}
