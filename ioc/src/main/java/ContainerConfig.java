import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContainerConfig {

    @Bean
    public Team player(){
        var player = new Team("John");
        player.setCoach("Henry");
        player.setPhysio("Vanessa");
        return player;
    }

    @Bean
    public Team player1(){
        var player = new Team("Jim");
        player.setCoach("Henry");
        player.setPhysio("Vanessa");
        return player;
    }

    @Bean
    public Coach coach(){
        var coach = new Coach("Henry");
        coach.setOwner("Frank");
        return coach;
    }


}
