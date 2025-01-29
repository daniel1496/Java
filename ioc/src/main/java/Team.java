public class Team {

    private String player;
    private String coach;

    private String physio;

    public Team(String player) {
        this.player = player;

    }

    public void play(){
        System.out.println("Player: " + player + " depends on coach " + coach + " and physio " + physio);
    }

    public String getName() {
        return player;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public void setplayer(String player) {
        this.player = player;
    }

    public String getPhysio() {
        return physio;
    }

    public void setPhysio(String physio) {
        this.physio = physio;
    }
}

