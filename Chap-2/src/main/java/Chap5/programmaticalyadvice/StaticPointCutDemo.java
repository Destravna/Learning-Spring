package Chap5.programmaticalyadvice;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import Chap5.programmaticalyadvice.common.GoodGuitarist;
import Chap5.programmaticalyadvice.common.GreatGuitarist;
import Chap5.programmaticalyadvice.common.Singer;

public class StaticPointCutDemo {
    public static void main(String[] args) {
        GoodGuitarist goodGuitarist = new GoodGuitarist();
        GreatGuitarist greatGuitarist = new GreatGuitarist();

        Singer proxyOne;
        Singer proxyTwo;

        Pointcut pc = new SimpleStaticPointCut();
        Advice advice = new SimpleAroundAdvice();
        Advisor advisor = new DefaultPointcutAdvisor(pc,  advice);
        
        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(goodGuitarist);
        proxyOne = (Singer)pf.getProxy();

        pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(greatGuitarist);
        proxyTwo = (Singer)pf.getProxy();

        proxyOne.sing();
        proxyTwo.sing();
        
    }
}
