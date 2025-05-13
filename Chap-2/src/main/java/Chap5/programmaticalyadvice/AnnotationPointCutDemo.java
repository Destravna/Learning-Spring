package Chap5.programmaticalyadvice;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import Chap5.programmaticalyadvice.common.GrammyGuitarist;
import Chap5.programmaticalyadvice.common.Singer;

public class AnnotationPointCutDemo {
    public static void main(String[] args) {
        var gg = new GrammyGuitarist();
        var pc = AnnotationMatchingPointcut.forMethodAnnotation(AdviceRequired.class);
        var advisor = new DefaultPointcutAdvisor(pc, new SimpleAroundAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(gg);
        pf.addAdvisor(advisor);
        Singer singer = (Singer) pf.getProxy();
        singer.sing();
    }
}
