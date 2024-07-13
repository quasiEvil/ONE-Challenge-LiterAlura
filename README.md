<p align="center"><img src="https://github.com/quasiEvil/ONE-DesafiosJava01/assets/140989367/629c3fbc-8343-4218-9383-cae3a8a329c1" height="60">
<br>
<img src="https://github.com/quasiEvil/ONE-DesafiosJava01/assets/140989367/ad683805-6a3c-4eb0-aee6-6c611b9d5340" height="10"> & <img src="https://github.com/quasiEvil/ONE-DesafiosJava01/assets/140989367/df751b45-3b7f-4297-a3c2-08d983be89b6" height="15">
</p>

# Fase 3 - Especialização Backend com Java e Spring

## LiterAlura | Catálogo de Livros
Este projeto Java utiliza Spring Boot para criar um catálogo de livros interativo, integrando-se à API Gutendex para obter dados de livros e autores. Permite operações como busca por título, listagem de livros cadastrados, e muito mais.

![Desafio LiterAlura Badge](https://github.com/user-attachments/assets/2422bc53-a2f8-4000-b467-37b22e149929)

👩🏻‍🎓 [Certificado de conclusão](https://cursos.alura.com.br/certificate/quasiEvil/spring-boot-challenge-literalura)

## Funcionalidades
- **Busca por Título:** Permite buscar livros pelo título diretamente na API Gutendex.
- **Cadastro de Livros:** Possibilita inserir novos livros no banco de dados local.
- **Listagem de Autores:** Exibe uma lista de todos os autores cadastrados.
- **Autores Vivos em Determinado Ano:** Exibe uma lista de todos os autores vivos em um ano específico.
- **Listagem de Livros em Determinado Idioma**: Exibe uma lista de livros em um idioma específico. 
- **Top 10:** Exibe os 10 livros mais baixados.
- **Busca de Autores por Nome:** Permite buscar todos os livros cadastrados de um determinado autor.

## Requisitos
- JDK (Kit de Desenvolvimento Java)
- IntelliJ IDEA ou qualquer IDE Java
- Banco de dados PostgreSQL

- ## Instalação e Configuração
1. Clone o repositório:
```bash
git clone https://github.com/quasiEvil/ONE-Challenge-LiterAlura.git

```
2. Abra o projeto no IntelliJ IDEA ou na sua IDE Java preferida.

3. Configure o arquivo `application.properties` no diretório `src/main/resources` com as configurações do banco de dados:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nome-do-banco
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=update
```
4. Execute o projeto Spring Boot.

## Uso
- Ao executar o projeto, você terá acesso a um menu interativo no terminal.
- Escolha uma das opções disponíveis para interagir com o catálogo de livros.
- Siga as instruções para realizar operações como busca, inserção e listagem de dados.

## Demo
![Java-LiterAlura](https://github.com/user-attachments/assets/02e0335c-e727-49ad-a744-0f2fe23cbaf6)

## Banco de dados (local)
<img width="968" alt="Captura de Tela 2024-07-13 às 15 12 54" src="https://github.com/user-attachments/assets/e3b5d17d-9f2a-47ff-915f-a12401853120">

<img width="755" alt="Captura de Tela 2024-07-13 às 15 13 15" src="https://github.com/user-attachments/assets/6d847d0a-52c2-45b0-8d26-580af6699431">

