package spring.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") //<-- All request to root should be handled by an instance of this class
public class MyRootController {

    // this handler method is responsible for handling GET / requests
    @GetMapping
    public String showHomePage(Model model){
        // adding to the Model is effectively adding to the request object which is visible to the view
        model.addAttribute("MyName", "Daniel");
        return "home"; //<-- the logical name of a view file
    }



}
