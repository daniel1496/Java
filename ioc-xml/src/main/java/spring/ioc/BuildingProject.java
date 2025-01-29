package spring.ioc;

public class BuildingProject extends Builder {

    private String name;
    private Tradesperson tradesperson;


    //aggregation as builder exists independently
    public BuildingProject(String name, Tradesperson tradesperson) {
        this.name = name;
        this.tradesperson = tradesperson;
    }

    public void start(){
        System.out.printf("Starting work on building project %s\n", name);
        tradesperson.work();

    }


}
