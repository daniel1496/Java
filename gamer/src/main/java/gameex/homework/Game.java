package gameex.homework;

import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "home_Team")
    private String homeTeam;
    @Column(name = "team_score")
    private int teamScore;
    @Column(name = "away_team")
    private String awayTeam;
    @Column(name = "away_score")
    private int awayScore;
    boolean matchComplete;


    public Game() {
    }

    public Game(String homeTeam, int teamScore, String awayTeam, int awayScore, boolean matchComplete) {
        this.homeTeam = homeTeam;
        this.teamScore = teamScore;
        this.awayTeam = awayTeam;
        this.awayScore = awayScore;
        this.matchComplete = matchComplete;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(int teamScore) {
        this.teamScore = teamScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public boolean isMatchComplete() {
        return matchComplete;
    }

    public void setMatchComplete(boolean matchComplete) {
        this.matchComplete = matchComplete;
    }
}
