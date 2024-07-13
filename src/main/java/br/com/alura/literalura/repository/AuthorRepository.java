package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByYearOfBirthLessThanEqualAndYearOfDeathIsNullOrYearOfDeathGreaterThan(int year1, int year2);
    Author findByName(String name);
}
