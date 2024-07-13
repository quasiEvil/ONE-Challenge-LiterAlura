package br.com.alura.literalura.service;

import br.com.alura.literalura.model.Author;
import br.com.alura.literalura.model.Book;
import br.com.alura.literalura.model.BookDTO;
import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

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
        System.out.println(data);
    }

    private void saveBookAndAuthorData(String data) throws JsonProcessingException {
        BookDTO bookData = dataConverter.getData(data, BookDTO.class);

        Author author;
        if (bookData.authors() != null && !bookData.authors().isEmpty()) {
            String authorName = bookData.authors().get(0).name();
            author = findOrCreateAuthor(authorName);
        } else {
            author = findOrCreateAuthor("Autor Desconhecido");
        }

        Book book = new Book(bookData.title(), String.join(", ", bookData.language()), bookData.downloadCount(), author);
        if (!bookRepository.existsByTitle(book.getTitle())) {
            bookRepository.save(book);
            author.addBook(book);
            System.out.println("Livro salvo: " + book);
        } else {
            System.out.println("Livro já existe: " + book);
        }
    }

    public Author findOrCreateAuthor(String name) {
        Author author = authorRepository.findByName(name);
        if (author == null) {
            author = new Author(name);
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
            System.out.println("\n*** Nenhum livro encontrado no banco de dados! ***");
        }
    }

    public void findRegisteredAuthors() {
        List<Author> registeredAuthors = authorRepository.findAll();
        if (!registeredAuthors.isEmpty()) {
            System.out.println("\nAutores cadastrados no banco de dados: ");
            for (Author author : registeredAuthors) {
                System.out.println("Autor: " + author.getName());
                System.out.println("---------------------------------------");
            }
        } else {
            System.out.println("\n*** Nenhum autor encontrado no banco de dados! ***");
        }
    }


    public void findAuthorsAliveByYear() {
        System.out.println("\nQual ano deseja pesquisar? ");
        var searchedYear = scanner.nextInt();
        var findAuthorsInDb = authorRepository.findByYearOfDeathLessThanEqual(searchedYear);
        if (!findAuthorsInDb.isEmpty()) {
            System.out.println("\n\nAutores vivos no ano de " + searchedYear);
            findAuthorsInDb.forEach(System.out::println);
        } else {
            System.out.println("\n*** Nenhum autor encontrado para esse ano! ***");
        }
    }

    public void findBooksByLanguage() {
        var registeredLanguages = bookRepository.searchLanguages();
        System.out.println("\nIdiomas disponíveis: ");
        registeredLanguages.forEach(System.out::println);
        var searchedLanguage = scanner.nextLine();
        bookRepository.findByLanguage(searchedLanguage).forEach(System.out::println);
    }

    public void findAuthorByName() {
        System.out.println("\nQual autor deseja procurar? ");
        var searchedAuthor = scanner.nextLine();

        boolean exists = authorRepository.existsByName(searchedAuthor);

        if (exists) {
            var author = authorRepository.findByName(searchedAuthor);
            System.out.println("Autor encontrado: " + author);
        } else {
            System.out.println("\n*** Autor não encontrado! ***");
        }
    }

    public void findTop10() {
        var top10 = bookRepository.findTop10ByOrderByDownloadCountDesc();
        top10.forEach(System.out::println);
    }
}


