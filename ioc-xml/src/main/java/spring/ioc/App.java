package spring.ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        // create an IoC Container
        // the constructor assumes the file is on the classpath
        var container = new ClassPathXmlApplicationContext("container-config.xml");

        // get a Greeter object out of the IoC Container
        // getBean is overloaded to accept a class or String name
        var greeter = container.getBean("greeter", Greeter.class);


        // use the Greeter to do something
        greeter.greet();

        // get the other Greeter object out of the IoC Container
        var greeter1 = container.getBean("greeter1", Greeter.class);

        // use the second greeter to do something
        greeter1.greet();

        // get an enquirer object of the IoC Container
        var enquirer = container.getBean(Enquirer.class);

        // use the Enquirer to do something
        enquirer.enquire();

        // get a BuildingProject out of the IoC Container
        // the expectation is that the BuildingProject is fully configured

        var buildingProject = container.getBean(BuildingProject.class);

        // use the BuildingProject to do something
        buildingProject.start();


    }

}