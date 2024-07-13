package br.com.alura.literalura;

import br.com.alura.literalura.ui.ConsoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.alura.literalura")
public class LiteraluraApplication implements CommandLineRunner {

	private final ConsoleMenu consoleMenu;

	@Autowired
	public LiteraluraApplication(ConsoleMenu consoleMenu) {
		this.consoleMenu = consoleMenu;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		consoleMenu.showMenu();
	}
}
