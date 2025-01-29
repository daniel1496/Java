package gameex.homework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class PlayerRepoTest {

    @Autowired
    PlayerRepo playerRepo;

    @Test
    public void testFindAll(){
        assertEquals(3, playerRepo.findAll().size());
    }

}
