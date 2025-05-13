package Chap5.programmaticalyadvice;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

import Chap5.programmaticalyadvice.common.GrammyGuitarist;

public class NameMethodPointCutAdvisorDemo {
    public static void main(String[] args) {
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor(new SimpleAroundAdvice());
        advisor.setMappedNames("rest", "sing");
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(new GrammyGuitarist());
        pf.addAdvisor(advisor);

        GrammyGuitarist gg = (GrammyGuitarist) pf.getProxy();
        gg.sing();
        gg.rest();
        gg.talk();

    }
}
