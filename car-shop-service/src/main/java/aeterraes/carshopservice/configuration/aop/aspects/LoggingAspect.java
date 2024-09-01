package aeterraes.carshopservice.configuration.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* aeterraes.carshopservice.service..*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Starting method: " + methodName);
    }

    @Around("execution(* aeterraes.carshopservice.service..*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Method " + methodName + " executed in " + duration + " ms");

        return result;
    }

    @AfterReturning(pointcut = "execution(* aeterraes.carshopservice.service..*(..))", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method " + methodName + " returned: " + result);
    }

    @After("execution(* aeterraes.carshopservice.service..*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method " + methodName + " executed");
    }
}
