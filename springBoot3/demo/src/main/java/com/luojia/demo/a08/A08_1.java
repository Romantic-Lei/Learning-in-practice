package com.luojia.demo.a08;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

@Configuration
public class A08_1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A08_1.class);
                context.getBean(MyService.class).doBusiness();
        context.close();
    }

    static class MyEvent extends ApplicationEvent {
        public MyEvent(Object source) {
            super(source);
        }
    }

    @Bean
    public SmartInitializingSingleton smartInitializingSingleton(ConfigurableApplicationContext context) {
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (String name : context.getBeanDefinitionNames()) {
                    Object bean = context.getBean(name);
                    for (Method method : bean.getClass().getMethods()) {
                        if (method.isAnnotationPresent(MyListener.class)) {
                            ApplicationListener listener = new ApplicationListener() {
                                @Override
                                public void onApplicationEvent(ApplicationEvent event) {
                                    System.out.println("========= " + event + " 监听到事件 =========");
                                    Class<?> eventType = method.getParameterTypes()[0];
                                    if (eventType.isAssignableFrom(event.getClass())) {
                                        try {
                                            method.invoke(bean, event);
                                        } catch (IllegalAccessException e) {
                                            throw new RuntimeException(e);
                                        } catch (InvocationTargetException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            };
                            context.addApplicationListener(listener);
                        }
                    }
                }
            }
        };
    }

    @Component
    static class MyService {
        private static final Logger log = Logger.getLogger(MyService.class.getName());

        @Autowired
        private ApplicationEventPublisher publisher;
        public void doBusiness() {
            log.info(Thread.currentThread().getName() + "主线业务");
            // 如果不设置线程池来执行的话，事件都是同步执行的，即都是由主线程执行
            publisher.publishEvent(new MyEvent("MyService.doBusiness()"));
        }
    }

    @Component
    static class SmsService {
        private static final Logger log = Logger.getLogger(SmsService.class.getName());

        @MyListener
        public void listener(MyEvent event) {
            log.info(Thread.currentThread().getName() + "支线业务：发送短信");
        }

    }

    @Component
    static class EmailApplicationListener implements ApplicationListener<MyEvent> {
        private static final Logger log = Logger.getLogger(EmailApplicationListener.class.getName());

        @Override
        public void onApplicationEvent(MyEvent event) {
            log.info(Thread.currentThread().getName() + "支线业务：发送邮件");
        }
    }

    @Component
    static class PayApplicationListener {
        private static final Logger log = Logger.getLogger(PayApplicationListener.class.getName());

        @EventListener
        public void listener(MyEvent event) {
            log.info(Thread.currentThread().getName() + "支线业务：支付");
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyListener {
    }

    @Bean
    public ThreadPoolTaskExecutor executor () {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("MyExecutor-");
        return executor;
    }

    // 注册这个Bean，就可以让整个事件异步执行了，不会阻塞主线程
    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(ThreadPoolTaskExecutor executor) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(executor);
        return multicaster;
    }
}
