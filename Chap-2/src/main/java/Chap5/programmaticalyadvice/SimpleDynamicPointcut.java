package Chap5.programmaticalyadvice;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import Chap5.programmaticalyadvice.common.GoodGuitarist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleDynamicPointcut extends DynamicMethodMatcherPointcut {
    private static final Logger logger = LoggerFactory.getLogger(SimpleDynamicPointcut.class);
    
    @Override
    public ClassFilter getClassFilter() {
        return cls -> cls == GoodGuitarist.class;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return ("sing".equals(method.getName()));
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        logger.debug("Dynamic check for: " + method.getName());
        String key = (String) args[0];
        return key.equalsIgnoreCase("C");
    }

}
