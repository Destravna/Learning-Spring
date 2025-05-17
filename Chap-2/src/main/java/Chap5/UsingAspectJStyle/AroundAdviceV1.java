package Chap5.UsingAspectJStyle;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class AroundAdviceV1 {
    private static final Logger logger = LoggerFactory.getLogger(AroundAdviceV1.class);

    @Pointcut("execution(* Chap5.UsingAspectJStyle..sing*(Chap5.UsingAspectJStyle.Guitar))")
    public void singExecution() {
    }

    @Around("singExecution()")
    public Object simpleAroundAdviceV1(ProceedingJoinPoint pjp) throws InterruptedException, Throwable {
        var singature = (MethodSignature) pjp.getSignature();
        logger.info("ABOUT to execute {} from {}", singature.getName(), singature.getDeclaringTypeName());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retValue = pjp.proceed();
        Thread.sleep(10);
        stopWatch.stop();
        logger.info("AROUND : time taken {}", stopWatch.getTotalTimeMillis());
        return retValue;
    }

    @Pointcut("execution(* Chap5.UsingAspectJStyle..execute*(..))")
    public void newDocumentaristExecute() {
    }

    @Around("newDocumentaristExecute()")
    public Object aroundExecutreAdvise(ProceedingJoinPoint pjp) throws InterruptedException, Throwable {
        var singature = (MethodSignature) pjp.getSignature();
        logger.info("ABOUT Execute to execute {} from {}", singature.getName(), singature.getDeclaringTypeName());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retValue = pjp.proceed();
        Thread.sleep(10);
        stopWatch.stop();
        logger.info("AROUND Execute : time taken {}", stopWatch.getTotalTimeMillis());
        return retValue;
    }

    // Binding args so that we can pass them directly
    // 1. Fix: Bind the Guitar argument using a parameter in the pointcut method
    @Pointcut("execution(* Chap5.UsingAspectJStyle..sing*(Chap5.UsingAspectJStyle.Guitar)) && args(guitar)")
    public void singExecutionWithArgs(Guitar guitar) {
    }

    // 2. Fix: Make sure the pointcut method name matches and the argNames align
    @Around(value = "singExecutionWithArgs(guitar)", argNames = "pjp,guitar")
    public Object aroundAdviceWithBindedArgs(ProceedingJoinPoint pjp, Guitar guitar) throws Throwable {
        var signature = (MethodSignature) pjp.getSignature();
        logger.info(" -=-> Before Executing: {} from {} with argument {}", signature.getName(),
                signature.getDeclaringTypeName(), guitar);

        Object retVal = pjp.proceed();

        logger.info(" -=-> After Executing: {} from {} with argument {}", signature.getName(),
                signature.getDeclaringTypeName(), guitar);

        return retVal;
    }

}
