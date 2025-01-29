package spring.jpa.entitymanagerrepo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class BookRepo {
    @PersistenceContext // <- like autowiring
    private EntityManager entityManager;

    public void addBook(Book book){
        entityManager.persist(book);

    }

    public Book getBookById(long id){
       return entityManager.find(Book.class, id);
    }

    public List<Book> getBooksByPartialTitle(String partialTitle){
        // the query is JPQL not SQL
        // you're querying your entity (Book Class) not the DB table(books)
        // you where clause must ref field names, not column names
        var query = entityManager.createQuery("from Book where title like ?1", Book.class);
        query.setParameter(1, "%" + partialTitle+ "%");
        return query.getResultList();
    }
}
