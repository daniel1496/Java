package spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MyRestController {

    // it is bad practice to wire a repo into a controller
    // the controller should only know about the service
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("greeting")
    public String getGreeting() {
        return "Hello world";
    }

    @GetMapping("secret")
    public String getSecret() {
        return "This is top secret!";
    }

    @GetMapping("admin")
    public String forAdministratorsOnly() {
        return "This is for administrators only!";
    }

    @PostMapping("message")
    public void processMessage(@RequestBody String message) {
        System.out.println(message);
    }

    @PostMapping("users")
    public User addNewUser(@RequestBody @Validated User user) {
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        // it seems that calling setPassword triggers validation again which is a problem
        // we would probably do our own validation of the plain text password in the service
        user.setPassword(encodedPassword);
        return userRepo.save(user);
    }
}
