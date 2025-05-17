package Chap5.UsingAspectJStyle;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
public class BeforeAdviceV1 {
    private static final Logger logger = LoggerFactory.getLogger(BeforeAdviceV1.class);

    @Before("execution(*  Chap5.UsingAspectJStyle..sing*(Chap5.UsingAspectJStyle.Guitar))")
    public void simpleBeforeAdvice(JoinPoint joinPoint){
        var singature = (MethodSignature) joinPoint.getSignature();
        for (Object arg: joinPoint.getArgs()){
            logger.info("arg : {}", arg);  
        }
        logger.info("> Executing: {} from {} with {}", singature.getName(), singature.getDeclaringTypeName(), singature.getParameterTypes());
       
    }

    //separating the pointcut 

    @Pointcut("execution(*  Chap5.UsingAspectJStyle..rest*(..))")
    void restPointCut(){

    }

    @Before("restPointCut()")
    public void simpleBeforeAdviceRest(JoinPoint joinPoint){
        var singature = (MethodSignature) joinPoint.getSignature();
        for (Object arg: joinPoint.getArgs()){ 
            logger.info("arg : {}", arg);  
        }
        logger.info("> Executing: {} from {} with {}", singature.getName(), singature.getDeclaringTypeName(), singature.getParameterTypes());

    }

    @Pointcut("bean(john*)")
    public void isJohn(){

    }

    @Before("restPointCut() && isJohn()")
    public void thisIsJohn(JoinPoint joinPoint){
        logger.info("This is JOHN SNOW");
    }


}
