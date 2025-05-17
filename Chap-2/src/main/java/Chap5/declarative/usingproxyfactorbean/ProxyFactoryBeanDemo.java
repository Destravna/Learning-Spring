package Chap5.declarative.usingproxyfactorbean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProxyFactoryBeanDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AopConfig.class);
        var doc1 = ctx.getBean("documentaristOne", Documentarist.class);
        var doc2 = ctx.getBean("documentaristTwo", Documentarist.class);
        doc1.execute();
        doc2.execute();
        ctx.close();

    }
}
