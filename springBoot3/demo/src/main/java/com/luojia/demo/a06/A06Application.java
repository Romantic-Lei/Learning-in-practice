package com.luojia.demo.a06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class A06Application {
    private static final Logger log = LoggerFactory.getLogger(MyBean.class);

    public static void main(String[] args) {
        /*
         * 1. Aware 接口用于注入一些与容器相关信息，例如
         *   a. BeanNameAware 注入 bean 的名字
         *   b. BeanFactoryAware 注入 BeanFactory容器
         *   c. ApplicationContextAware 注入 ApplicationContext容器
         *   d. EmbeddedValueResolverAware ${}
         */
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("myBean", MyBean.class);

        /*
         * 2. b、c、d 的功能用 @Autowired 就能实现，为什么还需要用Aware 接口呢
         *
         * 简单的说：
         *   a. @Autowired 的解析需要用到bean 后处理器，属于扩展功能
         *   b. 而 Aware 接口属于内置功能，不加任何扩展，Spring 就能识别
         *      某些情况下，，扩展功能会失效，而内置功能不会失效
         * 例如：
         *   在MyBean 类中，用Aware 注入ApplicationContext成功，而使用@Autowired 注入 ApplicationContext 失败
         *   MyBean 里面的第零、第五无法被输出
         *   除非在上面新增 context.registerBean(AutowiredAnnotationBeanPostProcessor.class); context.registerBean(CommonAnnotationBeanPostProcessor.class);
         */
        // 下面两个是针对 @Autowired 和 @PostConstruct 的后处理器，不加是不能解析MyBean类中这两个注解的
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        /*
         * 3. Java 配置类在添加了 bean 工厂后处理器后，
         * 你会发现传统接口方式的注入和初始化依然成功，而 @Autowired 和 @PostConstruct 的注入和初始化失败
         */
        context.registerBean("myConfig1", MyConfig1.class);
        context.registerBean(ConfigurationClassPostProcessor.class);

        /*
         * 学到了什么
         *   a. Aware 接口提供了一种【内置】的注入手段，可以注入 BeanFactory、ApplicationContext、BeanName
         *   b. InitializingBean 接口提供了一种【内置】的初始化手段
         *   c. 内置的注入和初始化不受扩展功能的影响，总是会被执行，因此 Spring 框架内部的类常用它们
         */
        context.registerBean("myConfig2", MyConfig2.class);

        context.refresh();
        context.close();


    }
}
