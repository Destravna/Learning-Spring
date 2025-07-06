package org.dhruv.Chap5.aspects;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dhruv.Chap5.exceptions.LimitReachedException;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
public class RateLimiterAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterAspect.class);

    private final Map<Method, List<Instant>> methodCalls = new ConcurrentHashMap<>();

    @Pointcut("@annotation(org.dhruv.Chap5.annotations.RateLimited)")
    public void rateLimitedMethods() {

    }

    @Around("rateLimitedMethods()")
    public Object rateLimit(ProceedingJoinPoint jp) throws Throwable {
        logger.info("IN rate limited");
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        List<Instant> methodCallHistory = methodCalls.get(method);
        boolean canExecute = true;
        if (methodCallHistory != null) {
            logger.info("{} CALL HISTORY SIZE {}", method.getName(), methodCallHistory.size());
            canExecute = methodExecuteControl(methodCallHistory);

        }
        if (canExecute) {
            methodCalls.computeIfAbsent(method, k -> new ArrayList<>()).add(Instant.now());
        } else {
            throw new LimitReachedException(method.getName() + "limit reached" + "Please try again after sometime");
        }

        return jp.proceed();
    }

    private boolean methodExecuteControl(List<Instant> callTimes) {
        Instant now = Instant.now();
        //only keep the calls in last 3 minutes
        callTimes.removeIf(time -> Duration.between(time, now).getSeconds() >= 180);
        if (callTimes.size() >= 3) {
            return false;
        }
        return true;
    }

}
