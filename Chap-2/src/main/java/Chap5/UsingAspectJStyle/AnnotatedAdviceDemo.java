package Chap5.UsingAspectJStyle;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AnnotatedAdviceDemo {


    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AnnotatedAdviceDemo.class);
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV1"));
        NewDocumentarist documentarist = ctx.getBean("documentarist", NewDocumentarist.class);
        documentarist.execute();
    }
}
