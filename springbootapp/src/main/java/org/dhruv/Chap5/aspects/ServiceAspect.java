package org.dhruv.Chap5.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ServiceAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

    @Pointcut("execution(* org.dhruv.Chap5.service.*.*(..))")
    void serviceMethodPointcut() {

    }

    @Before("serviceMethodPointcut()")
    void beforeAdvice(JoinPoint jp) {
        var signature = (MethodSignature) jp.getSignature();
        logger.info("started method : {}", signature.getName());
    }

    // accessing the method args
    @Pointcut("execution(* org.dhruv.Chap5.service.*.*(..)) && args(param)")
    void serviceMethodPointcutWithArg(Object param) {
    }

    @Before(value = "serviceMethodPointcutWithArg(param)", argNames = "jp, param")
    void beforeAdviceWithArg(JoinPoint jp, Object param) {
        var signature = (MethodSignature) jp.getSignature();
        logger.info("started method : {}", signature.getName());
        logger.info("param : {}", param);
    }

}
