package spring.recap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class CircleUI {

    // the CircleUI HAS-A CircleService; the CircleUI depends on the CircleService
    // the association is aggregation, the benefits of which include:
    // - the ability to swap CircleServices without having to change the UI
    // - the ability to unit test our classes
    // - the ability to exploit frameworks likes spring
    @Autowired // <-- This dependency should be automatically resolved
    private CircleService service;



    public void run(){

        var scanner = new Scanner(System.in);

        System.out.printf("Radius: ");
        var radius = scanner.nextDouble();

        System.out.printf("Diameter: %.2f\n", service.getDiameter(radius));
        System.out.printf("Circumference: %.2f\n", service.getCircumference(radius));
        System.out.printf("Area: %.2f\n", service.getArea(radius));

    }
}
