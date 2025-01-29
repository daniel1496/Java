package spring.homework.games;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// the IoC Container creates an implementation of this interface automatically
// the implementation uses an EntityManager internally to do the work
public interface GameRepo extends JpaRepository<Game, Long> {

    // in JPQL: from Game g where g.homeTeam = ? or g.awayTeam = ?;
    // in SQL: select * from games where home_team = ? or away_team = ?;
    List<Game> findByHomeTeamIgnoreCaseOrAwayTeamIgnoreCase(String homeTeam, String awayTeam);
}
