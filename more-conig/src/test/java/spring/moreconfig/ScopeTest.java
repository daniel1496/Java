package spring.moreconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ScopeTest {
    private ApplicationContext container = new AnnotationConfigApplicationContext(ContainerConfig.class);

    @Test
    public void testSingletonScope(){
        var bean1 = container.getBean(MySingletonComponent.class);
        var bean2 = container.getBean(MySingletonComponent.class);
        assertSame(bean1, bean2);
    }
    @Test
    public void testPrototypeScope(){
        var bean1 = container.getBean(MyProtorypeComponent.class);
        var bean2 = container.getBean(MyProtorypeComponent.class);
        assertNotSame(bean1, bean2);
    }
}
