package spring.homework.games;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class GameRepoTest {

    @Autowired
    private GameRepo gameRepo;

    @Test
    public void testFindByHomeTeamIgnoreCaseOrAwayTeamIgnoreCase() {
        var games = gameRepo.findByHomeTeamIgnoreCaseOrAwayTeamIgnoreCase(
                "Liverpool", "Liverpool");
        assertEquals(2, games.size());
    }
}
