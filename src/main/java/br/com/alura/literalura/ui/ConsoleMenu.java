package br.com.alura.literalura.ui;

import br.com.alura.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final BookService bookService;

    @Autowired
    public ConsoleMenu(BookService bookService) {
        this.bookService = bookService;
    }

    public void showMenu() {
        String menu = """
                
                *****************************************
                L I T E R A L U R A | CATÁLOGO DE LIVROS
                *****************************************
                
                1 - Buscar livro por título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em determinado ano
                5 - Listar livros em determinado idioma
                6 - Top 10 mais baixados
                7 - Buscar autores por nome
                
                0 - Sair
                **********************************************
                
                """;
        while (true) {
            System.out.println(menu);
            int choice = readIntegerInput("Escolha uma opção: ");

            switch (choice) {
                case 1:
                    System.out.print("Digite o título do livro: ");
                    String title = scanner.nextLine();
                    try {
                        bookService.searchBook(title);
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar o livro: " + e.getMessage());
                    }
                    break;
                case 2:
                    bookService.findRegisteredBooks();
                    break;
                case 3:
                    bookService.findRegisteredAuthors();
                    break;
                case 4:
                    bookService.findAuthorsAliveByYear();
                    break;
                case 5:
                    bookService.findBooksByLanguage();
                    break;
                case 6:
                    bookService.findTop10();
                    break;
                case 7:
                    bookService.findAuthorByName();
                    break;
                case 0:
                    System.out.println("\nSaindo... até a próxima!");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n*** Opção inválida. Tente novamente. ***\n");
            }
        }
    }

    private int readIntegerInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n*** Por favor, digite um número válido. ***\n");
            }
        }
    }
}
