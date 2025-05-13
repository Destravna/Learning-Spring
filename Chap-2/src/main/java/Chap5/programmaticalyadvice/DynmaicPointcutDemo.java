package Chap5.programmaticalyadvice;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import Chap5.programmaticalyadvice.common.GoodGuitarist;
import Chap5.programmaticalyadvice.common.GrammyGuitarist;
import Chap5.programmaticalyadvice.common.Singer;

public class DynmaicPointcutDemo {
    public static void main(String[] args) {
        GrammyGuitarist target = new GrammyGuitarist();

        Advisor advisor = new DefaultPointcutAdvisor(new SimpleDynamicPointcut(), new SimpleAroundAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);

        GrammyGuitarist proxy = (GrammyGuitarist) pf.getProxy();
        proxy.sing("C");
        proxy.sing("c");
        proxy.sing("E");
        proxy.sing();
        proxy.sing2();
    }
}
