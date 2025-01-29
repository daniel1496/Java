package spring.ioc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* This class contains the metadata to be used to configure the IoC Container.
 * This is An alternative to providing said metadata in an XML File.
 * However, both XML and Java config may be used together.
 *
 * @Configuration should be present on those classes that produce bean definition.
 * in XML a bean definition is a bean tag eg <bean class = ".." />
 *
 * NB: it is unlikely that you will code @Bean methods for your own classes.
 * You will code them for third-party classes that you want the IoC Container to instantiate.
 */

@Configuration
public class ContainerConfig {

    @Bean // Marks the method as a bean definition
    public Greeter greeter(){
        var greeter =  new Greeter("Dan");
        greeter.setGreeting("Hello");
        return greeter;
    }

    @Bean // Marks the method as a bean definition
    public Greeter greeter1(){
        var greeter =  new Greeter("Dave");
        greeter.setGreeting("G'day");
        return greeter;
    }

    @Bean
    public BuildingProject buildingProject(){
        return new BuildingProject("My Building Project", electrician());
    }

    @Bean
    public Builder builder(){
        return new Builder();
    }

    @Bean
    public Plumber plumber(){
        return new Plumber();
    }

    @Bean
    public Electrician electrician(){
        return new Electrician();
    }
}
