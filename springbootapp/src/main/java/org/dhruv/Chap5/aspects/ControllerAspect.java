package org.dhruv.Chap5.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
public class ControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    // pointcut for all the methods in a class with this annotation
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerPointCut() {
    }

    @Around("restControllerPointCut()")
    public Object measureApiTimeAdvice(ProceedingJoinPoint pjp) throws Throwable{
        var signature = (MethodSignature) pjp.getSignature();
        StopWatch sw = new StopWatch();
        sw.start();
        Object returnValue = pjp.proceed();
        sw.stop();
        logger.info("time taken by {} = {}ms", signature.getName(), sw.getTotalTimeMillis());
        return returnValue;
    }
}
