package spring.moreconfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        var container = new AnnotationConfigApplicationContext(ContainerConfig.class);
        container.getBean(Greeter.class).greet();
    }
}
