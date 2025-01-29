package spring.moreconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("australia")
public class AussieGreeter implements Greeter{

    @Value("${greeting.australian}")
    private String greeting;
    @Override
    public void greet() {
        System.out.println(greeting);
    }
}
