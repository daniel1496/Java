import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        var container = new AnnotationConfigApplicationContext(ContainerConfig.class);

        var player = container.getBean("player", Team.class);
        var player1 = container.getBean("player1", Team.class);
        var coach = container.getBean("coach",Coach.class);

        player.play();
        player1.play();
        coach.coach();

    }
}
