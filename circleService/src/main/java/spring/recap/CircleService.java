package spring.recap;

import org.springframework.stereotype.Component;

@Component
public class CircleService {

    public double getDiameter(double radius){
        return radius * 2;
    }

    public double getCircumference(double radius){
        return 2 * Math.PI * radius;
    }

    public double getArea(double radius){
        return Math.PI * Math.pow(radius, 2);
    }

}
