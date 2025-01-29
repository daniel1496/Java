package spring.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        // create an IoC Container where the metadata exists in a java class - ContainerConfig
        var container = new AnnotationConfigApplicationContext(ContainerConfig.class);

        // get a Greeter1 object out of the container
        var greeter = container.getBean("greeter", Greeter.class);

        // use the first Greeter object to do something
        greeter.greet();

        // get a Greeter2 object out of the container
        var greeter1 = container.getBean("greeter1", Greeter.class);

        // use the second Greeter object to do something
        greeter1.greet();

        // get a BuildingProject object out of the container
        var buildingProject = container.getBean(BuildingProject.class);

        // use the BuildingProject object to do something
        buildingProject.start();
    }

}