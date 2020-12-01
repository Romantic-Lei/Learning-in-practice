package com.romanticlei.study.interview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
// spring4 需要写上@RunWith(SpringRunner.class)，spring5不需要
//@RunWith(SpringRunner.class)
public class InterviewApplicationTests {

    @Resource
    private CalcService calcService;

    // spring5导入的 @Test包路径与spring4不一致
    @Test
    public void testAop4(){
        System.out.println("Spring 版本" + SpringVersion.getVersion() + "\nSpringBoot 版本" + SpringBootVersion.getVersion());

        System.out.println();
        /**
         * 我是环绕通知之前 AAA
         * *********@Before 我是前置通知MyAspect
         * =========>CalcServiceImpl5
         * 我是环绕通知之后 BBB
         * *********@After 我是后置通知MyAspect
         * *********@AfterReturning 我是返回后通知MyAspect
         *
         * Process finished with exit code 0
         */
        calcService.div(10, 2);

        /**
         *我是环绕通知之前 AAA
         * *********@Before 我是前置通知MyAspect
         * *********@After 我是后置通知MyAspect
         * *********@AfterThrowing 我是异常通知MyAspect
         */
        System.out.println();
        calcService.div(10, 0);
    }

    @Test
    public void testAop5(){
        System.out.println("Spring 版本" + SpringVersion.getVersion() + "\nSpringBoot 版本" + SpringBootVersion.getVersion());

        System.out.println();

        /**
         * 我是环绕通知之前 AAA
         * *********@Before 我是前置通知MyAspect
         * =========>CalcServiceImpl5
         * *********@AfterReturning 我是返回后通知MyAspect
         * *********@After 我是后置通知MyAspect
         * 我是环绕通知之后 BBB
         */
        calcService.div(10, 2);

        System.out.println();

        /**
         * 我是环绕通知之前 AAA
         * *********@Before 我是前置通知MyAspect
         * *********@AfterThrowing 我是异常通知MyAspect
         * *********@After 我是后置通知MyAspect
         */
        calcService.div(10, 0);
    }

}
