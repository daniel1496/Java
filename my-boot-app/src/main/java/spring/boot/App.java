package spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
* @SpringBookApplication is effectively the same as;
* @Configuration
* @ComponentScan
* THIS IS YOUR CONFIG CLASS
*
 */


@SpringBootApplication
public class App {

	public static void main(String[] args) {


		var container = SpringApplication.run(App.class, args);
		var greeter = container.getBean(Greeter.class);

		greeter.greet();
	}


}
