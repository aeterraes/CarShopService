package aeterraes.carshopservice.configuration.aop.aspects;

import aeterraes.carshopservice.configuration.aop.annotation.Audit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuditingAspect {

    @Before("@annotation(audit)")
    public void beforeAuditMethod(JoinPoint joinPoint, Audit audit) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("Auditing method: " + methodName + " with arguments: " + Arrays.toString(args));
    }

    @Around("@annotation(audit)")
    public Object auditMethod(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        System.out.println("Auditing method: " + methodName + " with arguments: " + Arrays.toString(args));

        Object result = joinPoint.proceed();

        System.out.println("Method executed: " + methodName);

        return result;
    }

    @AfterReturning(pointcut = "@annotation(audit)", returning = "result")
    public void afterReturningAuditMethod(JoinPoint joinPoint, Audit audit, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method " + methodName + " returned: " + result);
    }

    @After("@annotation(audit)")
    public void afterAuditMethod(JoinPoint joinPoint, Audit audit) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Audit completed for method: " + methodName);
    }
}

