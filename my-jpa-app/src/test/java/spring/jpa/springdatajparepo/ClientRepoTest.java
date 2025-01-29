package spring.jpa.springdatajparepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import spring.springdatajparepo.Client;
import spring.springdatajparepo.ClientRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional // <- each method is wrapped as transactional
@ActiveProfiles("dev")
public class ClientRepoTest {


    @Autowired
    private ClientRepo repo; //<- ref to the auto generated implementation

    @Test
    public void testSave(){
        var client = repo.save(new Client("Daniel", "Test@ASD.com"));
        assertEquals(4L, client.getId());
    }
    @Test
    public void testFindAll(){
        var clients = repo.findAll();
        assertEquals(3, clients.size());
    }
    @Test
    public void testFindById(){
        //findById returns an optional<Client> because the ID may not exist
        //the get method returns the Client or throws an RunTimeException
        var client = repo.findById(1L).get();
        assertEquals("Dave Smith", client.getName());
        System.out.println(client.getName());
    }
    @Test
    public void testDeleteById(){
        repo.deleteById(3L);
        assertFalse(repo.findById(3L).isPresent());
    }
    @Test
    @Commit
    public void testUpdate(){
        var client = repo.findById(2L).get();
        client.setEmail("Sarah@live.com");
    }

}
