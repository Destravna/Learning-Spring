package Chap5.programmaticalyadvice;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import Chap5.programmaticalyadvice.common.GrammyGuitarist;

public class NamePointCutDemo {
    public static void main(String[] args) {
        GrammyGuitarist dhruv = new GrammyGuitarist();
        NameMatchMethodPointcut pc = new NameMatchMethodPointcut();
        pc.addMethodName("sing");
        pc.addMethodName("rest");

        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAroundAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(dhruv);
        pf.addAdvisor(advisor);

        GrammyGuitarist guitarist = (GrammyGuitarist) pf.getProxy();
        guitarist.sing();
        guitarist.talk();

    }
}
