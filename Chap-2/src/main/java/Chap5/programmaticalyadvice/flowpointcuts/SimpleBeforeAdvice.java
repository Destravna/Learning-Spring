package Chap5.programmaticalyadvice.flowpointcuts;

import java.lang.reflect.Method;
import org.springframework.aop.MethodBeforeAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleBeforeAdvice implements MethodBeforeAdvice {
    private static final Logger logger = LoggerFactory.getLogger(SimpleBeforeAdvice.class);
    
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.info("before method: {}", method);      
    }
}
