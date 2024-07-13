package br.com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authors")
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @Column(name = "year_of_death")
    private Integer yearOfDeath;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author() {}

    public Author(String name) {
        this.name = name;
    }

    public Author(AuthorDTO authorDTO) {
        this.name = authorDTO.name();
        this.yearOfBirth = authorDTO.yearOfBirth();
        this.yearOfDeath = authorDTO.yearOfDeath();
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    @Override
    public String toString() {
        return "\nNome: " + getName() +
                "\nData De Nascimento: " + (getYearOfBirth() != null ? getYearOfBirth() : "Desconhecido") +
                "\nData De Falecimento: " + (getYearOfDeath() != null ? getYearOfDeath() : "Desconhecido");
    }
}
