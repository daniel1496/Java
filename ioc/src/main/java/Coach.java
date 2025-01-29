public class Coach {

    private String coach;
    private String owner;

    public void coach(){
        System.out.println("Coach " + coach + " Depends on owner " + owner);
    }
    public Coach(String coach) {
        this.coach = coach;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
