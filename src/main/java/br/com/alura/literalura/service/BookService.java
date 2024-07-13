package br.com.alura.literalura.service;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class BookService {

    private final ApiClient apiClient;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final DataConverter dataConverter;
    private Scanner scanner = new Scanner(System.in);

    public BookService(ApiClient apiClient, AuthorRepository authorRepository, BookRepository bookRepository, DataConverter dataConverter) {
        this.apiClient = apiClient;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.dataConverter = dataConverter;
    }

    public void searchBook(String search) throws JsonProcessingException {
        String data = apiClient.getData("https://gutendex.com/books?search=" + search.replace(" ", "%20"));
        saveBookAndAuthorData(data);
    }

    private void saveBookAndAuthorData(String data) throws JsonProcessingException {
        BookDTO bookData = dataConverter.getData(data, BookDTO.class);

        Author author;
        if (bookData.authors() != null && !bookData.authors().isEmpty()) {
            AuthorDTO authorDTO = bookData.authors().get(0);
            author = findOrCreateAuthor(authorDTO);
        } else {
            author = findOrCreateAuthor(new AuthorDTO("Autor Desconhecido", null, null));
        }

        Language language;
        try {
            language = Language.fromCode(bookData.language().get(0).toString());
        } catch (IllegalArgumentException e) {
            language = Language.UNKNOWN;
            System.out.println("Idioma desconhecido: " + bookData.language().get(0));
        }

        Book book = new Book(bookData.title(), language, bookData.downloadCount(), author);

        if (!bookRepository.existsByTitle(book.getTitle())) {
            bookRepository.save(book);
            author.getBooks().add(book);
            System.out.println("\nLIVRO SALVO COM SUCESSO: ");
            System.out.println("---------------------------------------");
            System.out.println("Título: " + book.getTitle());
            System.out.println("Idioma: " + language);
            System.out.println("Autor: " + author.getName());
            System.out.println("Número de downloads: " + book.getDownloadCount());
            System.out.println();
        } else {
            System.out.println("\n*** LIVRO JÁ CADASTRADO NO BANCO DE DADOS: " + book + "***\n");
        }
    }


    public Author findOrCreateAuthor(AuthorDTO authorDTO) {
        Author author = authorRepository.findByName(authorDTO.name());
        if (author == null) {
            author = new Author(authorDTO);
            authorRepository.save(author);
        }
        return author;
    }


    public void findRegisteredBooks() {
        List<Book> registeredBooks = bookRepository.findAll();
        if (!registeredBooks.isEmpty()) {
            System.out.println("\nLivros cadastrados no banco de dados: ");
            registeredBooks.forEach(System.out::println);
        } else {
            System.out.println("\n*** NENHUM LIVRO ENCONTRADO NO BANCO DE DADOS! ***\n");
        }
    }

    public void findRegisteredAuthors() {
        List<Author> registeredAuthors = authorRepository.findAll();
        if (!registeredAuthors.isEmpty()) {
            System.out.println("\nAutores cadastrados no banco de dados: ");
            for (Author author : registeredAuthors) {
                System.out.println("\nAutor: " + author.getName());
                System.out.println("---------------------------------------");
            }
        } else {
            System.out.println("\n*** NENHUM AUTOR ENCONTRADO NO BANCO DE DADOS! ***\n");
        }
    }

    public void findAuthorsAliveByYear() {
        System.out.println("\nQual ano deseja pesquisar? ");
        try {
            int searchedYear = scanner.nextInt();
            scanner.nextLine(); // Consumes newline left-over
            var findAuthorsInDb = authorRepository.findByYearOfBirthLessThanEqualAndYearOfDeathIsNullOrYearOfDeathGreaterThan(searchedYear, searchedYear);
            if (!findAuthorsInDb.isEmpty()) {
                System.out.println("\n\nAutores vivos no ano de " + searchedYear + ":\n");
                findAuthorsInDb.forEach(System.out::println);
            } else {
                System.out.println("\n*** NENHUM AUTOR ENCONTRADO PARA ESSE ANO! ***");
            }
        } catch (InputMismatchException e) {
            System.out.println("\n*** ENTRADA INVÁLIDO! ***");
        }
    }

    public void findBooksByLanguage() {
        var registeredLanguages = bookRepository.searchLanguages();
        System.out.println("\nIdiomas disponíveis: ");
        registeredLanguages.forEach(System.out::println);

        System.out.println("\nDigite o idioma desejado (código de 2 letras): ");
        String searchedLanguage = scanner.nextLine().toLowerCase();

        try {
            Language language = Language.fromCode(searchedLanguage); // Converts language code to Language enum
            List<Book> books = bookRepository.findByLanguageCode(searchedLanguage); // Passes the language code

            if (!books.isEmpty()) {
                System.out.println("\n " + searchedLanguage + ": \n");
                books.forEach(System.out::println);
            } else {
                System.out.println("\n*** NENHUM LIVRO ENCONTRADO PARA ESSE IDIOMA! ***");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n*** " + e.getMessage() + " ***");
        }
    }


    public void findAuthorByName() {
        System.out.println("\nQual autor deseja procurar? ");
        String searchedAuthor = scanner.nextLine();

        Author author = authorRepository.findByName(searchedAuthor);

        if (author != null) {
            System.out.println("\nAUTOR ENCONTRADO:");
            System.out.println(author);
        } else {
            System.out.println("\n*** AUTOR NÃO ENCONTRADO! ***");
        }
    }


    public void findTop10() {
        List<Book> top10 = bookRepository.findTop10ByOrderByDownloadCountDesc();
        if (!top10.isEmpty()) {
            System.out.println("\nTOP 10 LIVROS MAIS BAIXADOS: ");
            top10.forEach(System.out::println);
        } else {
            System.out.println("\n*** NENHUM LIVRO ENCONTRADO NO TOP10! ***");
        }
    }
}