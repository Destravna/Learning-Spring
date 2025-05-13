package Chap5.programmaticalyadvice;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import Chap5.programmaticalyadvice.common.GoodGuitarist;

public class SimpleStaticPointCut extends StaticMethodMatcherPointcut{
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return ("sing".equals(method.getName()));
    }

    @Override
    public ClassFilter getClassFilter() {
        return cls -> cls == GoodGuitarist.class;
    }

}
