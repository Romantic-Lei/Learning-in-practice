package com.luojia.demo.a49;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Configuration
public class A49 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A49.class);
        context.getBean(MyService.class).doBusiness();
        context.close();
    }

    static class MyEvent extends ApplicationEvent {
        public MyEvent(Object source) {
            super(source);
        }
    }

    @Component
    static class MyService {
        private static final Logger log = LoggerFactory.getLogger(MyService.class);

        @Autowired
        private ApplicationEventPublisher publisher;
        public void doBusiness() {
            log.info(Thread.currentThread().getName() + "主线业务");
            // 如果不设置线程池来执行的话，事件都是同步执行的，即都是由主线程执行
            publisher.publishEvent(new MyEvent("MyService.doBusiness()"));
        }
    }

    @Component
    static class SmsService implements ApplicationListener<MyEvent> {
        private static final Logger log = LoggerFactory.getLogger(SmsService.class);
        @Override
        public void onApplicationEvent(MyEvent event) {
            log.info(Thread.currentThread().getName() + "支线业务：发送短信");
        }
    }

    @Component
    static class EmailApplicationListener implements ApplicationListener<MyEvent> {
        private static final Logger log = LoggerFactory.getLogger(EmailApplicationListener.class);
        @Override
        public void onApplicationEvent(MyEvent event) {
            log.info(Thread.currentThread().getName() + "支线业务：发送邮件");
        }
    }

    @Component
    static class PayApplicationListener implements ApplicationListener<MyEvent> {
        private static final Logger log = LoggerFactory.getLogger(PayApplicationListener.class);

        @Override
        public void onApplicationEvent(MyEvent event) {
            log.info(Thread.currentThread().getName() + "支线业务：支付");
        }
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

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(ConfigurableApplicationContext context, ThreadPoolTaskExecutor executor) {
        return new AbstractApplicationEventMulticaster() {
            private List<GenericApplicationListener> listeners = new ArrayList<>();
            // 收集监听器
            @Override
            public void addApplicationListenerBean(String listenerBeanName) {
                System.out.println(listenerBeanName);
                ApplicationListener listener = context.getBean(listenerBeanName, ApplicationListener.class);
                System.out.println("listener 对象" + listener);
                // 获取监听器的泛型参数，getGeneric(0)是获取到第几个参数，如果是0可以不写
                ResolvableType type = ResolvableType.forClass(listener.getClass()).getInterfaces()[0].getGeneric(0);
                System.out.println("generic type " + type);
                // 将原始的 listener 封装为支持事件类型检查的listener
                GenericApplicationListener genericApplicationListener = new GenericApplicationListener() {
                    @Override
                    public void onApplicationEvent(ApplicationEvent event) {
                        // 调用原始listener方法的onApplicationEvent
                        executor.submit(() -> {
                            listener.onApplicationEvent(event);
                        });
                    }

                    // 是否支持某事件类型
                    @Override
                    public boolean supportsEventType(ResolvableType eventType) {
                        return type.isAssignableFrom(eventType);
                    }
                };
                // 将包装后的 listeners 集合中
                listeners.add(genericApplicationListener);
            }

            // 广播事件，即是发事件用的，即上面我们发送事件的时候 publisher.publishEvent() 就会调用下面的方法
            @Override
            public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
                for (GenericApplicationListener listener : listeners) {
                    if (listener.supportsEventType(ResolvableType.forClass(event.getClass()))) {
                        listener.onApplicationEvent(event);
                    }
                }
            }
        };
    }

    abstract static class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster {

        @Override
        public void addApplicationListener(ApplicationListener<?> listener) {

        }

        @Override
        public void addApplicationListenerBean(String listenerBeanName) {

        }

        @Override
        public void removeApplicationListener(ApplicationListener<?> listener) {

        }

        @Override
        public void removeApplicationListenerBean(String listenerBeanName) {

        }

        @Override
        public void removeApplicationListeners(Predicate<ApplicationListener<?>> predicate) {

        }

        @Override
        public void removeApplicationListenerBeans(Predicate<String> predicate) {

        }

        @Override
        public void removeAllListeners() {

        }

        @Override
        public void multicastEvent(ApplicationEvent event) {

        }

        @Override
        public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {

        }
    }

}
