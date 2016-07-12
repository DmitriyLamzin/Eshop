package org.lamzin.eshop.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by Dmitriy on 20.06.2016.
 */
@Aspect
public class TestAspects {
    private final Logger log = Logger.getLogger(this.getClass());

    @Before("execution(* org.lamzin.eshop.dao.catalog.*.*(..))")
    public void beforeMethodLog (JoinPoint joinPoint) throws Throwable {

        StringBuffer logMessage = getMethodDescription(joinPoint);

        logMessage.append(" is called...");

        log.info(logMessage.toString());
    }


    @AfterReturning(value = "execution(* org.lamzin.eshop.dao.catalog.*.*(..))",
            returning = "retVal")
    public void afterInvocationLogger(JoinPoint joinPoint, Object retVal){
        StringBuffer logMessage = getMethodDescription(joinPoint);
        logMessage.append(" is complete, ret. val.: ");
        if (retVal != null)
            logMessage.append(retVal.toString());
        else logMessage.append("null");
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

    @AfterThrowing(value = "execution(* org.lamzin.eshop.dao.catalog.*.*(..))", throwing = "throwable")
    public void afterThrowingLogger(JoinPoint joinPoint, Throwable throwable){
        StringBuffer logMessage = getMethodDescription(joinPoint);
        logMessage.append(" throws: ").
                append(throwable.getMessage());
        log.error(logMessage.toString());
    }
}
