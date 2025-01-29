package spring.mcb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MyService {

   // @Autowired
  //  @Qualifier("file") // <- use the implementation with the file qualifier
    private MyRepo myRepo;

    // the IoC Container will try to autowire by type and then by name
    // using a @Qualifier is a better option because it doesnt require changes to code
    public MyService(@Qualifier("file") MyRepo myRepo) {
        this.myRepo = myRepo;
    }
}
