package Chap3.InjectingValuesUsingSpel;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InjectSpelDemo {

    @Value("#{injectSimpleConfig.name.toUpperCase()}")
    private String name;

    @Value("#{injectSimpleConfig.age + 1}")
    private int age;
    @Value("#{injectSimpleConfig.height}")
    private float height;
    @Value("#{injectSimpleConfig.developer}")
    private boolean developer;
    @Value("#{injectSimpleConfig.ageInSeconds}")
    private Long ageInSeconds;
    @Value("#{injectSimpleConfig.nums}")
    private List<Integer> lst;

    @Override
    public String toString() {
        
        return "Name: " + name + "\n"
                + "Age: " + age + "\n"
                + "Age in Seconds: " + ageInSeconds + "\n"
                + "Height: " + height + "\n"
                + "Is Developer?: " + developer + "\n"
                + "nums:"  + lst;    
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(InjectSimpleConfig.class, InjectSpelDemo.class);
        ctx.refresh();
        InjectSpelDemo injectSpelDemo = ctx.getBean(InjectSpelDemo.class);
        System.out.println(injectSpelDemo);
    }

}
