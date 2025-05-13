package Chap5.programmaticalyadvice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleAfterAdvice implements AfterReturningAdvice {

    private static final Logger logger = LoggerFactory.getLogger(SimpleAfterAdvice.class);

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        logger.info("After : offer standing ovation");
    }

}
