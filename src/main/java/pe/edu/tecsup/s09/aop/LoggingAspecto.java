package pe.edu.tecsup.s09.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspecto {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspecto.class);

    @Pointcut("execution(* pe.edu.tecsup.s09.web..*(..)) || execution(* pe.edu.tecsup.s09.services..*(..))")
    public void appLayer() {}

    @Around("appLayer()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().toShortString();
        long start = System.currentTimeMillis();
        log.info("[INICIO] {}", method);
        try {
            Object result = pjp.proceed();
            long ms = System.currentTimeMillis() - start;
            log.info("[FIN] {} ({} ms)", method, ms);
            return result;
        } catch (Throwable ex) {
            long ms = System.currentTimeMillis() - start;
            log.error("[ERROR] {} ({} ms) - {}", method, ms, ex.getMessage());
            throw ex;
        }
    }
}