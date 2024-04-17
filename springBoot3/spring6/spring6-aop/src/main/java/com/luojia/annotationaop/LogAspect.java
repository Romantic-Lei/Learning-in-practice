package com.luojia.annotationaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// @Aspect 表示这个类是一个切面类
@Aspect
// @Component 注解保证这个切面类能够放入IOC容器
@Component
public class LogAspect {

    /**
     * 设置切入点和通知类型
     * 通知类型：前置 @Before(value = "切入点表达式，配置切入点")
     * 切入点表达式：execution(访问修饰符 增强方法返回类型 增强方法所在类全路径.方法名称(方法参数))
     */
    @Before(value = "execution(public int com.luojia.annotationaop.CalculatorImpl.*(int, int))")
    public void beforeMethod() {
        System.out.println("LogAspect --> 前置通知");
    }

    // 后置 @After()
    // @After(value = "execution(public int com.luojia.annotationaop.CalculatorImpl.*(..))")
    // 重用切入点表达式 @After(value = "pointCut()")
    // 当引用其他类中的切入点表达式时，用下面的方法
    @After(value = "com.luojia.annotationaop.LogAspect.pointCut()")
    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("LogAspect --> 后置通知，方法名称：" + methodName + ", 参数：" + Arrays.toString(args));
    }

    // 返回 @AfterReturning
    @AfterReturning(value = "execution(* com.luojia.annotationaop.CalculatorImpl.*(..))",returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("LogAspect --> 返回通知，方法名称：" + methodName + "， 目标方法返回结果：" + result);
    }

    // 异常 @AfterThrowing
    // 目标方法出现异常，这个通知执行
    @AfterThrowing(value = "execution(* com.luojia.annotationaop.CalculatorImpl.*(..))", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("LogAspect --> 异常通知，方法名称：" + methodName + "， 目标方法返回异常结果：" + ex);
    }

    // 环绕 @Around()
    @Around("execution(* com.luojia.annotationaop.CalculatorImpl.*(..))")
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
    @Pointcut(value = "execution(* com.luojia.annotationaop.CalculatorImpl.*(..))")
    public void pointCut() {

    }

}
