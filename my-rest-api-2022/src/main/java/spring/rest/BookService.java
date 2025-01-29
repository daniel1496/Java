package spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepo repo;

    public List<Book> getBooks(int pageNum, int pageSize) {
        return repo.findAll(PageRequest.of(pageNum, pageSize)).toList();
    }

    public List<Book> getBooksByPartialTitle(String partialTitle) {
        return repo.findByTitleContainsIgnoreCase(partialTitle);
    }

    public Book getBookById(long id) {
        return repo.findById(id).orElseThrow(NoSuchBookException::new);
    }

    public List<Review> getReviewsForBook(long bookId) {
        var book = repo.findById(bookId).orElseThrow(NoSuchBookException::new);
        return book.getReviews();
    }

    public List<Review> getReviewsForBookByRating(long bookId, Rating rating) {
        var book = repo.findById(bookId).orElseThrow(NoSuchBookException::new);
        return book.getReviews().stream().filter(review -> review.getRating() == rating).collect(Collectors.toList());
    }

    public Book addNewBook(Book book) {
        if (repo.findByTitleIgnoreCaseAndAuthorIgnoreCase(book.getTitle(), book.getAuthor()).isEmpty()) {
            return repo.save(book);
        } else {
            throw new DuplicateBookException();
        }
    }

    public Book updateBookById(long id, Book updatedBook) {
        // NB: don't call getBookById in this class - it won't be included in the transaction
        var book = repo.findById(id).orElseThrow(NoSuchBookException::new);
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        return book;
    }

    public Book deleteBookById(long id) {
        var book = repo.findById(id).orElseThrow(NoSuchBookException::new);
        repo.deleteById(id);
        return book;
    }
}
