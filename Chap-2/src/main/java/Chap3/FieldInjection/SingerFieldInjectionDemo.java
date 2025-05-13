package Chap3.FieldInjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SingerFieldInjectionDemo {
    public static void main(String[] args) {
        ApplicationContext apx =  new AnnotationConfigApplicationContext(SingerFieldInjectionDemo.class);
        Singer singer = apx.getBean(Singer.class);
        singer.sing();
        // apx.close();
        
    }
}
