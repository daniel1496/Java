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
public class ReviewRepoTest {

    @Autowired
    JpaBookRepository bookRepo;

    JpaReviewRepository reviewRepo;

    @Test
    public void testSaveWithNewBook(){
        var book = new Book("Harry Potter", "J.K Rowling");
        var review = new Review("Very good", book);
        reviewRepo.save(review);

        assertEquals(4, bookRepo.findAll().size());
        assertEquals(4, reviewRepo.findAll().size());
    }

}
