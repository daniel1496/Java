package spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        var cotaniner = new AnnotationConfigApplicationContext(ContainerConfig.class);
        cotaniner.getBean(MyComponent.class).go();
    }
}
