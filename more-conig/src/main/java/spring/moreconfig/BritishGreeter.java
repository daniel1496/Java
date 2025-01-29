package spring.moreconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("britian")
public class BritishGreeter implements  Greeter {

    // dont forget the ${} property placeholder
    // without it the literal value greeting.british will be assigned to the field
    @Value("${greeting.british}")
    private String greeting;


    @Override
    public void greet() {
        System.out.println(greeting);
    }
}
