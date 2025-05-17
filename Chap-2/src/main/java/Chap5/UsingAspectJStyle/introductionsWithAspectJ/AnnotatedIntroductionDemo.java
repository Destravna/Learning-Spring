package Chap5.UsingAspectJStyle.introductionsWithAspectJ;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import Chap5.UsingAspectJStyle.AnnotatedAdviceDemo;
import Chap5.UsingAspectJStyle.GrammyGuitarist;
import Chap5.UsingAspectJStyle.Guitar;
import Chap5.programmaticalyadvice.common.Singer;

@Configuration
public class AnnotatedIntroductionDemo {
    private static final Logger logger = LoggerFactory.getLogger(AnnotatedIntroductionDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AnnotatedIntroductionDemo.class, AnnotatedAdviceDemo.class);
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("annotatedIntroduction"));
        Guitar guitar = new Guitar("gibson");
        var guitarist = ctx.getBean("johnMayer", GrammyGuitarist.class);
        assertTrue(guitarist instanceof Singer);
        assertTrue(guitarist instanceof Performer);
        Performer performer = (Performer) guitarist;
        performer.perform();

    }
}
