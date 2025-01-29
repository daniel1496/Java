package spring.recap;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        // here we are creating IoC Container
        //this thing both creates the objects we need and stores them
        var container = new AnnotationConfigApplicationContext(ContainerConfig.class);

        // get a CircleUI from the container and run it
        container.getBean(CircleUI.class).run();
    }
}
