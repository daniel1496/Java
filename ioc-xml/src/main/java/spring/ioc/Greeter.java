package spring.ioc;

public class Greeter {

    private String recipient;
    private String greeting;


    public Greeter(String recipient) {
        this.recipient = recipient;
    }

    public void greet(){

        System.out.printf("%s %s\n", greeting, recipient);
    }

    public String getRecipient() {

        return recipient;
    }

    public void setRecipient(String recipient) {

        this.recipient = recipient;
    }

    public String getGreeting() {

        return greeting;
    }

    public void setGreeting(String greeting) {

        this.greeting = greeting;
    }
}
