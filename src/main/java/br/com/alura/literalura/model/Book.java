package br.com.alura.literalura.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(name = "language", nullable = false)
    private String languageCode; // Armazena o código do idioma

    @Transient
    private Language language; // Enum Language que não será armazenado diretamente no banco de dados

    private Integer downloadCount;

    @ManyToOne
    private Author author;

    public Book(String title, Language language, Integer downloadCount, Author author) {
        this.title = title;
        this.languageCode = language.getCode();
        this.language = language;
        this.downloadCount = downloadCount;
        this.author = author;
    }

    @PostLoad
    private void postLoad() {
        this.language = Language.fromCode(this.languageCode);
    }

    @PrePersist
    @PreUpdate
    private void prePersistOrUpdate() {
        this.languageCode = this.language.getCode();
    }

    @Override
    public String toString() {
        return "\n---------------------------------------" +
                "\nTítulo: " + title +
                "\nIdioma: " + language.getCode() +
                "\nAutor: " + author.getName() +
                "\nNúmero de downloads: " + downloadCount;
    }
}
