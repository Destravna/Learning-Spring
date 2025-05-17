package Chap5.declarative.usingproxyfactorbean;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditAdvice implements MethodBeforeAdvice {
    private static final Logger logger = LoggerFactory.getLogger(AuditAdvice.class);
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.info("executing method {}", method.getName());        
    }

}
