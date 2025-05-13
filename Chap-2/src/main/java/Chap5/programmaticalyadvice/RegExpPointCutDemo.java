package Chap5.programmaticalyadvice;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import Chap5.programmaticalyadvice.common.GrammyGuitarist;


public class RegExpPointCutDemo {
    public static void main(String[] args) {
        GrammyGuitarist d = new GrammyGuitarist();
        JdkRegexpMethodPointcut pc = new JdkRegexpMethodPointcut();
        pc.setPattern(".*sing.*");
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAroundAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(d);
        pf.addAdvisor(advisor);
        GrammyGuitarist proxy = (GrammyGuitarist) pf.getProxy();
        proxy.sing();
        proxy.sing2();
    }
}
