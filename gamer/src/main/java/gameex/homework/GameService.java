package gameex.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameService {

    @Autowired
    GameRepo repo;
    @Autowired
    GameValidator validator;

    public void addGame(Game game) throws GameExistsException{
        validator.isValid(game);
        if(repo.findByHomeTeamIgnoreCaseOrAwayTeamIgnoreCase(game.getHomeTeam(), game.getAwayTeam()).isPresent()){
            throw new GameExistsException();
        }
        repo.save(game);

    }

    public List<Game> findTeamIgnoreCase(String homeTeam, String awayTeam){
        return repo.findTeamIgnoreCase(homeTeam, awayTeam);
    }



}
