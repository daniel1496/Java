package spring.componentsanddi;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan // <- Have the IoC Container scan for classes annotated with @Component
public class ContainerConfig {
}
