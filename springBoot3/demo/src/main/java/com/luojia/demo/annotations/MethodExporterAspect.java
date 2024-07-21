package com.luojia.demo.annotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class MethodExporterAspect {

    // 只要带着MethodExporter注解，就会自动触发 Around 业务逻辑
    // 和 @Around("execution()")区别在于 execution后面的方法触发就都会执行切面
    @Around("@annotation(com.luojia.demo.annotations.MethodExporter)")
    public Object methodExporter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object retValue = null;

        long startTime = System.currentTimeMillis();
        System.out.println("---Around 环绕通知前置方法");

        retValue = proceedingJoinPoint.proceed();//放行方法
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        // 1.获得重载后的方法名
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        // 2.确定方法名后获得该方法上面配置的注解标签 MethodExporter
        MethodExporter methodExporterAnnotation = method.getAnnotation(MethodExporter.class);

        // 其实只要能进入到这个方法，这里就不会为空
        if (methodExporterAnnotation != null) {
            // 3.获得方法里面的形参信息
            StringBuffer sb = new StringBuffer();
            // 接口里面的参数值，例如下标0就对应第一个参数的输入值
            Object[] args = proceedingJoinPoint.getArgs();

            DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
            // 接口里面的具体参数名称
            String[] parameterNames = discoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                sb.append(parameterNames[i] + "\t" + args[i] + "\t");
            }
            // 4.将返回结果retValue序列化
            String jsonResult = null;
            if (retValue != null) {
                jsonResult = new ObjectMapper().writeValueAsString(retValue);
            } else {
                jsonResult = null;
            }

            log.info("\n方法分析上报中" +
                    "\n类名方法名：" + proceedingJoinPoint.getTarget().getClass().getName()+"."+proceedingJoinPoint.getSignature().getName()+"()"+
                    "\n执行耗时：" + costTime + "毫秒" +
                    "\n输出参数：" + sb + "" +
                    "\n返回结果：" + jsonResult + "" +
                    "\nover"
                    );
            System.out.println("---Around 环绕通知后置方法");
        }
        return retValue;
    }
}
