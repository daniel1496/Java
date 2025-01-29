package spring.mcb;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        var container = new AnnotationConfigApplicationContext(ContainerConfig.class);
    }
}
