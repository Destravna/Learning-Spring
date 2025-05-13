package Chap3.ValueInjection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SimpleValueInjectionDemo {
    @Value("John Mayer")
    private String name;
    @Value("40")
    private int age;
    @Value("1.92")
    private float height;
    @Value("false")
    private boolean developer;
    @Value("1241401112")
    private Long ageInSeconds;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SimpleValueInjectionDemo.class);
        ctx.refresh();
        SimpleValueInjectionDemo si = ctx.getBean(SimpleValueInjectionDemo.class);
        System.out.println(si);
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
                + "Age: " + age + "\n"
                + "Age in Seconds: " + ageInSeconds + "\n"
                + "Height: " + height + "\n"
                + "Is Developer?: " + developer;    
    }

}
