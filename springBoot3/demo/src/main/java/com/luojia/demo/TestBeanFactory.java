package com.luojia.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class TestBeanFactory {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", beanDefinition);

        System.out.println("<<<<<<<<<<<<<<<========原始BeanFactory=======>>>>>>>>>>>>>>>");
        for (String name : beanFactory.getBeanDefinitionNames()) {
            // 在这里只能获取到了 config Bean对象，没有获取到 bean1 和 bean2
            // 即原始的 beanFactory 是没有解析 spring 注解的能力的
            // 输出： config
            System.out.println(name);
        }

        System.out.println("<<<<<<<<<<<<<<<=======添加一些常用的后置处理器========>>>>>>>>>>>>>>>");
        // 给BeanFactory 添加一些常用的后置处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        for (String name : beanFactory.getBeanDefinitionNames()) {
            // 此时的输出就会多一些spring 内置的一些处理器
            // 这里面输出的bean 都有一个共同的类型，BeanFactoryPostProcessor
            // 输出结果如下：
            /* config
            org.springframework.context.annotation.internalConfigurationAnnotationProcessor
            org.springframework.context.annotation.internalAutowiredAnnotationProcessor
            org.springframework.context.annotation.internalCommonAnnotationProcessor
            org.springframework.context.event.internalEventListenerProcessor
            org.springframework.context.event.internalEventListenerFactory */
            System.out.println(name);
        }

        System.out.println("<<<<<<<<<<<<<<<=======获取加入内置处理器后bean 的信息========>>>>>>>>>>>>>>>");
        // BeanFactory 后处理器主要功能，补充了一些bean 定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            // 执行bean 的处理器
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
        // 获取bean1 里面注入的其他bean信息，这里无法获取，输出会为null，因为无法解析@Autowired @Resource这些注解
//        System.out.println(beanFactory.getBean(Bean1.class).getBean2());

        // Bean 后处理器，针对 bean 的生命周期的各个阶段提供扩展，例如对@Autowired @Resource进行解析
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            // 添加Bean 后处理器
            beanFactory.addBeanPostProcessor(beanFactoryPostProcessor);
        });

        for (String name : beanFactory.getBeanDefinitionNames()) {
            // 这里就获取到加入的bean1 和 bean2
            // 即spring的一些扩展功能是由他的后置处理器来实现的
            // 这里输出如下：
            /*config
            org.springframework.context.annotation.internalConfigurationAnnotationProcessor
            org.springframework.context.annotation.internalAutowiredAnnotationProcessor
            org.springframework.context.annotation.internalCommonAnnotationProcessor
            org.springframework.context.event.internalEventListenerProcessor
            org.springframework.context.event.internalEventListenerFactory
            bean1
            bean2 */
            System.out.println(name);
        }

        System.out.println("<<<<<<<<<<<<<<<=======获取bean1 的信息========>>>>>>>>>>>>>>>");
        // 这里就能获取到bean1 里面注入的bean2信息，这里才开始创建到哪里输出信息依次为：
        // 构造 bean1
        // 构造 bean2
        // com.luojia.demo.TestBeanFactory$Bean2@3e6ef8ad
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());

        /**
         * 学到了什么呢：
         * a. BeanFactory 不会做的事：
         *      1.不会主动调用 BeanFactor 后处理器
         *      2.不会主动调用 Bean 后处理器
         *      3.不会主动初始化单例
         *      4.不会主动调用销毁方法
         *      5.不会解析 beanFactory 还不会解析 ${} 与 #{}
         * b. bean 后处理器会有排序的逻辑
         */

    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {
        public Bean1() {
            System.out.println("构造 bean1");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }
    }

    static class Bean2 {
        public Bean2() {
            System.out.println("构造 bean2");
        }
    }

}
