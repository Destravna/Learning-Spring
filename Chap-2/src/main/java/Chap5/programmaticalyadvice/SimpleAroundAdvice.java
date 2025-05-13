package Chap5.programmaticalyadvice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class SimpleAroundAdvice implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SimpleAroundAdvice.class);
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.info("Around : starting timer");
        StopWatch sw = new StopWatch();
        logger.info("invoking {}", invocation.getMethod().getName());
        sw.start(invocation.getMethod().getName());
        Object returnvalue = invocation.proceed();
        Thread.sleep(10);
        sw.stop();
        logger.info("Around concert duration = {}", sw.getTotalTimeMillis());
        return returnvalue;
    }
}
