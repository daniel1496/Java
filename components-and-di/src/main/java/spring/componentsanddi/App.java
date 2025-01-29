package spring.componentsanddi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        var container = new AnnotationConfigApplicationContext(ContainerConfig.class);

        // get MyComponent object out of the IoC Container
        var myComponent = container.getBean(MyComponent.class);

        // get My Service object out of the IoC Container
        var myService = container.getBean(MyService.class);
        assert myService.getMyRepo() != null;
    }
}
