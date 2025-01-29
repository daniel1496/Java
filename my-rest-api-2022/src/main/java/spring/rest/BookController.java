package spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Request handling flow:
 * 1. A request is received by the servlet container (Tomcat)
 * 2. The servlet container forwards the request to the DispatcherServlet (Spring's front controller)
 * 3. The DispatcherServlet delegates to a HandlerMapping bean to map the request to a controller method
 * 4. The DispatcherServlet calls the controller method
 * 5. The DispatcherServlet uses a MessageConverter (jackson-databind) to serialise the String[]
 * 6. The DispatcherServlet builds the response and passes it back to the servlet container
 * 7. The servlet container issues the response
 */

@RestController // <!-- composed of @Controller and @ResponseBody (return values should be serialised into the response body)
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    // if any handler throws a NoSuchBookException set 404 as the response status code
    @ExceptionHandler(NoSuchBookException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleNoSuchBookException() {}

    // if any handler throws a DuplicateBookException set 409 as the response status code
    @ExceptionHandler(DuplicateBookException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private void handleDuplicateBookException() {}

    // don't implement GET /books without pagination - you may overwhelm your client with data
    @GetMapping
    public List<Book> getBooks(
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        // the return value is serialised (converted to a String)
        //this happens because the class is annotated with @ResponseBody
        return service.getBooks(pageNum, pageSize);
    }

    // a request for GET /books?title= will be mapped to this method
    @GetMapping(params = "title")
    public List<Book> getBooksByPartialTitle(@RequestParam String title) {
        return service.getBooksByPartialTitle(title);
    }

    // TODO add handlers to facilitate searching by author and by both title and author

    @GetMapping("/{id}") // <!-- the path here is appended to the path set on the class
    public Book getBookById(@PathVariable long id) {
        return service.getBookById(id);
    }

    @GetMapping("/{bookId}/reviews") // <!-- the path here is appended to the path set on the class
    public List<Review> getReviewsForBook(@PathVariable long bookId) {
        return service.getReviewsForBook(bookId);
    }

    /*
     * TODO
     * Add to the Review class a field for rating, e.g. 0-5 stars
     * Modify the schema.sql and data.sql files to facilitate the rating
     * Add an endpoint to the controller to enable the client to search reviews by rating
     */

    @GetMapping(path = "/{bookId}/reviews", params = "rating") // <!-- the path here is appended to the path set on the class
    public List<Review> getReviewsForBookByRating(@PathVariable long bookId, Rating rating) {
        return service.getReviewsForBookByRating(bookId, rating);
    }

    /*
     * @Validated must be accompanied by javax.validation annotations on your entity class.
     * If validation fails Spring will produce a response with a 400 status code.
     * If you add an Errors parameter then you must handle invalid objects manually.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addNewBook(@RequestBody @Validated Book book) {
        return service.addNewBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBookById(@PathVariable long id, @RequestBody Book updatedBook) {
        return service.updateBookById(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    public Book deleteBookById(@PathVariable long id){
        return service.deleteBookById(id);
    }

    // Optional TODO
    // can you facilitate searching by author?
}
