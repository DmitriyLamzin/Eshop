package org.lamzin.eshop.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by Dmitriy on 02.04.2016.
 */
@Aspect
public class ServiceLoggingAspect {

    private final Logger log = Logger.getLogger(this.getClass());

    @Before("execution(* org.lamzin.eshop.services.*.*(..))")
    public void beforeMethodLog (JoinPoint joinPoint) throws Throwable {

        StringBuffer logMessage = getMethodDescription(joinPoint);

        logMessage.append(" is called...");

        log.info(logMessage.toString());
    }


    @AfterReturning(value = "execution(* org.lamzin.eshop.services.*.*(..))",
            returning = "retVal")
    public void afterInvocationLogger(JoinPoint joinPoint, Object retVal){
        StringBuffer logMessage = getMethodDescription(joinPoint);
        logMessage.append(" is complete, ret. val.: ");
        if (retVal == null) {
            logMessage.append("null");
        } else
            logMessage.append(retVal.toString());

        log.debug(logMessage.toString());
    }

    private StringBuffer getMethodDescription(JoinPoint joinPoint) {
        StringBuffer logMessage = new StringBuffer();
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");
        // append args
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logMessage.append(arg).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }
        logMessage.append(")");
        return logMessage;
    }

    @AfterThrowing(value = "execution(* org.lamzin.eshop.services.*.*(..))", throwing = "throwable")
    public void afterThrowingLogger(JoinPoint joinPoint, Throwable throwable){
        StringBuffer logMessage = getMethodDescription(joinPoint);
        logMessage.append(" throws: ").
                append(throwable.getMessage());
        log.error(logMessage.toString());
    }
}
