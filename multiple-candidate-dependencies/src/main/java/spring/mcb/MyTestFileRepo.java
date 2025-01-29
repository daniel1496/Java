package spring.mcb;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("file") // <- additional metadata to help the IoC Container choose between implementations
//@Primary //<-- this implementation should take precedence over all others
public class MyTestFileRepo {
}
