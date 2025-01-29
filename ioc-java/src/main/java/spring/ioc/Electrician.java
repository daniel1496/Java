package spring.ioc;

public class Electrician extends Tradesperson{

    @Override
    public void work() {
        System.out.println("Electrifying...");
    }
}
