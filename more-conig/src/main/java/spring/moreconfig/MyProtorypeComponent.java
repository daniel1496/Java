package spring.moreconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@Scope("Prototype")
public class MyProtorypeComponent {


    /*
        Singleton components should be thread-safe
        In a web app n theards (workers) are likely to execute your component code simultaneously
        That can cause problems with data that is shared between threads.
        Fields should be final and their values immutable
        That is, the fields should be constant and should ref. immutable objects
        Local variables are OK because each thread (worker) get its own local variables.
     */

    private int num; // not thread-safe because it is not final
    private final String[] strings = new String[] {"Abc","def","ghi"}; // not thread safe because object is mutable.
    private final String string = "Hello"; // Thread safe


}
