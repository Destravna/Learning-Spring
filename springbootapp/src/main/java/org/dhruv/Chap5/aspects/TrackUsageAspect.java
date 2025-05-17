package org.dhruv.Chap5.aspects;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
public class TrackUsageAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(TrackUsageAspect.class);

    private Map<String, Integer> methodCallCount = new HashMap<>();

    @Pointcut("@annotation(org.dhruv.Chap5.annotations.TrackUsage)")
    void trackUsageMethods(){

    }

    public Map<String, Integer> getMethodCallCount() {
        return methodCallCount;
    }

    @Before("trackUsageMethods()")
    public void updateMap(JoinPoint jp){
        MethodSignature signature = (MethodSignature) jp.getSignature();
        methodCallCount.merge(signature.getName(), 1, Integer::sum);
        logger.info("method called {}", signature.getName());
    }

}
