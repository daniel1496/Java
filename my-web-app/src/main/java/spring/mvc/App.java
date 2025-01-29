package spring.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	/*
	*
	* The HTTP Request handling flow:
	* 1. The Request is received by the servlet container.
	* 2. The servlet container passes the request to the DispatcherServlet.
	* 3. The DispatherServlet delagetes to a HandlerMapping bean to determine which Controller should be process handle the request.
	* 4. The DispatcherServlet calls the Controllers handler method.
	* 5. The Controller handles the request by calling Service methods.
	* 6. The Controller returns the name of a view to the DispatcherServlet.
	* 7. The DispatcherServlet delagates to a ViewResolver to resolve the view name to a file
	* 8. The DispatcherServlet builds the view
	* 9. The Servlet container issues the response
	*
	 */


	public static void main(String[] args) {
		SpringApplication.run(App.class, args);


	}
}
