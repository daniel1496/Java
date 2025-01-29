package spring.homework.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameRepo gameRepo;

    public List<Game> findGamesByTeam(String team) {
        return gameRepo.findByHomeTeamIgnoreCaseOrAwayTeamIgnoreCase(team, team);
    }
}
