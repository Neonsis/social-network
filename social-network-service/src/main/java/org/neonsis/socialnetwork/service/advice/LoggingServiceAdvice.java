package org.neonsis.socialnetwork.service.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingServiceAdvice {

    private final Logger log = LoggerFactory.getLogger(LoggingServiceAdvice.class);

    @Pointcut(value = "execution(* org.neonsis.socialnetwork.service.impl..*(..) )")
    public void serviceLogPointcut() {
    }

    @Pointcut(value = "execution(* org.neonsis.socialnetwork.service.security..*(..) )")
    public void serviceSecurityLogPointcut() {
    }

    @Around("serviceLogPointcut() || serviceSecurityLogPointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] array = pjp.getArgs();
        log.debug("service method invoked " + className + " : " + methodName + "()" + " - Arguments : "
                + mapper.writeValueAsString(array));
        Object object = pjp.proceed();
        log.debug(className + " : " + methodName + "()" + " - Response : "
                + mapper.writeValueAsString(object));
        return object;
    }
}
