package Chap3.withstypes;

import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class HelloWorldConfigurationWithStypes {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfigurationWithStypes.class);
        MessageRenderer mr = ctx.getBean(StdOutMessageRendererConstructorInjection.class);
        mr.render();
    }

}
