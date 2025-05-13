package Chap5.programmaticalyadvice.flowpointcuts;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;

import Chap5.programmaticalyadvice.SimpleAfterAdvice;
import Chap5.programmaticalyadvice.common.GrammyGuitarist;

public class ComposablePointCutDemo {
    private static final Logger logger = LoggerFactory.getLogger(ComposablePointCutDemo.class);
    public static void main(String[] args) {
        GrammyGuitarist gg = new GrammyGuitarist();
        ComposablePointcut pc = new ComposablePointcut(ClassFilter.TRUE, new SingMethodMatcher());
        pc.union(new TalkMethodMatcher());
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAfterAdvice());
        var proxy = new ProxyFactory();
        proxy.setTarget(gg);
        proxy.addAdvisor(advisor);

        GrammyGuitarist guitarist = (GrammyGuitarist) proxy.getProxy();

        guitarist.sing();
        guitarist.talk();
    }
}   



class SingMethodMatcher extends StaticMethodMatcher {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return (method.getName().startsWith("si"));
    }
}

class TalkMethodMatcher extends StaticMethodMatcher {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return (method.getName().startsWith("ta"));
    }
}