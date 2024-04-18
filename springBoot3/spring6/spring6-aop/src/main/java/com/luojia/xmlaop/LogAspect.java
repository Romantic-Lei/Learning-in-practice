package com.luojia.xmlaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// @Component 注解保证这个切面类能够放入IOC容器
@Component
public class LogAspect {

    /**
     * 前置通知
     */
    public void beforeMethod() {
        System.out.println("LogAspect --> 前置通知");
    }

    /**
     * 后置通知
      */
    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("LogAspect --> 后置通知，方法名称：" + methodName + ", 参数：" + Arrays.toString(args));
    }

    /**
     *返回通知
     */
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("LogAspect --> 返回通知，方法名称：" + methodName + "， 目标方法返回结果：" + result);
    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "execution(* com.luojia.xmlaop.CalculatorImpl.*(..))", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("LogAspect --> 异常通知，方法名称：" + methodName + "， 目标方法返回异常结果：" + ex);
    }

    /**
     * 环绕通知
     */
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object result = null;
        try {
            System.out.println("LogAspect --> 环绕通知，目标方法之前执行，方法名：" + methodName + ", 参数：" + Arrays.toString(args));
            result = joinPoint.proceed();

            System.out.println("环绕通知，目标方法返回值之后执行");
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("环绕通知，目标方法出现异常执行");
        } finally {
            System.out.println("环绕通知，目标方法执行完毕后执行");
        }
        return result;
    }

    // 重用切入点表达式
    @Pointcut(value = "execution(* com.luojia.xmlaop.CalculatorImpl.*(..))")
    public void pointCut() {

    }

}
