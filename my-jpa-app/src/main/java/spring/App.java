package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.springdatajparepo.ClientRepo;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
	var container = SpringApplication.run(App.class, args);
	var clientRepo = container.getBean(ClientRepo.class);

	var clients = clientRepo.findAll();


	}
}
