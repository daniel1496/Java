package gameex.homework;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepo extends JpaRepository<Game, Long> {

    Optional<Game> findByHomeTeamIgnoreCaseOrAwayTeamIgnoreCase(String homeTeam, String awayTeam);

    List<Game> findTeamIgnoreCase(String homeTeam, String awayTeam);

}
