package spring.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect  // <-- Marks the class as containing cross cutting concern code
public class MyAspect {

    @Pointcut("execution(void spring.aop.MyComponent.go())")
    private void myComponentGo(){}

    // the value passed here is a pointcut which targets the methods to be advised
    // from this the IoC Container will determine that MyComponent is to be proxied
    @Before("myComponentGo()")
    public void beforeGo(){
        System.out.println("Going...");
    }

    @After("myComponentGo()")
    public void afterGo(){
        System.out.println("Gone!");
    }
}
