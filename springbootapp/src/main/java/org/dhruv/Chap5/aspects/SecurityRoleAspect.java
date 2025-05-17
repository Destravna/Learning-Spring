package org.dhruv.Chap5.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dhruv.Chap5.SecurityContext;
import org.dhruv.Chap5.annotations.RequireRole;
import org.dhruv.Chap5.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
public class SecurityRoleAspect {
    private static final Logger logger = LoggerFactory.getLogger(SecurityRoleAspect.class);
    @Autowired
    SecurityContext securityContext;

    @Pointcut("@annotation(org.dhruv.Chap5.annotations.RequireRole)")
    void requireRoleMethods() {

    }

    @Around("@annotation(requireRole)")
    public Object authorizeUser(ProceedingJoinPoint pjp, RequireRole requireRole) throws Throwable {
        String requiredRole = requireRole.value();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        logger.info("method called -> {}", methodSignature.getName());
        logger.info("requiredRole -> {}", requiredRole);

        String currentUserRole = securityContext.getUserRole();
        logger.info("currentUserRole -> {}", currentUserRole);

        if (!requiredRole.equals(currentUserRole)) {
            throw new UnauthorizedException("Access denied. Required role: " + requiredRole);
        }

        return pjp.proceed();
    }

}
