package spring.jpa.entitymanagerrepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.jpa.entitymanagerrepo.Book;
import spring.jpa.entitymanagerrepo.BookRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional // <- wrap each test in a tx and roll it back after the test method completes.
public class BookRepoTest {

    @Autowired
    private BookRepo repo;

    @Test
    public void testAddBook(){
        repo.addBook(new Book("My Book", "Daniel"));
        var book = repo.getBookById(1L);
        assertEquals("My Book", book.getTitle());
    }

    @Test
    public void testGetBookPartialTitle(){
        repo.addBook(new Book("My Book", "Daniel"));
        var books = repo.getBooksByPartialTitle("Book");
        assertEquals(1, books.size());
    }
}
