package spring.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank // <!-- the title must not be null/empty/whitespace only
    @Pattern(regexp = "[A-z\\s]{3,}") // <!-- the title must contain 3 or more alpha characters
    private String title;

    @NotBlank // <!-- the title must not be null/empty/whitespace only
    @Pattern(regexp = "[A-z\\s]{3,}") // <!-- the title must contain 3 or more alpha characters
    private String author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore // <!-- don't serialise the reviews
    private List<Review> reviews;

    private Book() {} // <!-- required by hibernate and jackson (message converter)

    public Book(String title, String author) {
        this();
        this.title = title;
        this.author = author;
        this.reviews = new ArrayList<>();
    }

    public void addReview(String user, String content, Rating rating) {
        reviews.add(new Review(this, user, content, rating));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
