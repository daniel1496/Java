package jpaex3.myjpaex3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class BookRepoTest {

    @Autowired
    private JpaBookRepository bookrepo;

    @Test
    public void testFindAll(){
        assertEquals(3, bookrepo.findAll().size());
    }
    @Test
    public void testFindById(){
        var book = bookrepo.findById(1L).get();
        assertEquals(3, book.getReviews().size());
    }

}
