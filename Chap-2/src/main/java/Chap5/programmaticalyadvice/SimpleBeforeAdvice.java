package Chap5.programmaticalyadvice;

import java.lang.reflect.Method;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class SimpleBeforeAdvice implements MethodBeforeAdvice {
    
    private static final Logger logger  = LoggerFactory.getLogger(SimpleBeforeAdvice.class);
    
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.info("Before set up concert hall");
    }
}
