package spring.recap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
* A component is a class that Spring's IoC Container should instantiate
* A component is a class that does some of the App's work.
* A component is NOT  a data class e.g. Book/Movie/Client etc.
* Component is a class that represents something
* A bean is an instance of component
* A bean is a an object that is stored inside Springs IoC Container
 */


@Configuration // <-- marks the class as containing bean definitions
@ComponentScan // <-- instruct the IoC Container to scan for @Component classes
public class ContainerConfig {


}
