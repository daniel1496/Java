package spring.rest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);

    List<Book> findByTitleContainsIgnoreCase(String partialTitle);
}
