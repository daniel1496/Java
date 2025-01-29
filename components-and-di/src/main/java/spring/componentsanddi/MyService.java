package spring.componentsanddi;

import org.springframework.stereotype.Component;

@Component
public class MyService {

    private MyRepo myRepo;

    public MyService(MyRepo myRepo) {
        this.myRepo = myRepo;
    }

    public MyRepo getMyRepo() {
        return myRepo;
    }
}


