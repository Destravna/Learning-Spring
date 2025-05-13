package Chap5.programmaticalyadvice;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import Chap5.programmaticalyadvice.common.GrammyGuitarist;

public class AspectJPointCutDemo {
    public static void main(String[] args) {
        GrammyGuitarist gg = new GrammyGuitarist();
        var pc = new AspectJExpressionPointcut();
        pc.setExpression("execution(* sing*(..))");
        var advisor = new DefaultPointcutAdvisor(pc, new SimpleAroundAdvice());

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(gg);
        pf.addAdvisor(advisor);
        GrammyGuitarist g = (GrammyGuitarist) pf.getProxy();
        g.sing();
        g.rest();
        
    }

}
