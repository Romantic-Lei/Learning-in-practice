package com.luojiapay.payment;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class PaymentApplication {

    public static void main(String[] args) throws Exception {
        // SpringApplication.run(PaymentApplication.class, args);

        /*
        以下项目演示都和支付项目无关，演示的只是springboot的启动过程
        构建 SpringApplication 对象
        System.out.println("1. 演示获取 Bean Definition 源");
        SpringApplication spring = new SpringApplication(PaymentApplication.class);
        // 这里演示手动设置 Bean Definition 源
        // spring.setSources(Set.of("classpath:a.xml"));

        System.out.println("2. 演示推断应用类型");
        // 查看当前应用类型，因为是私有的，所以这通过反射获取
        Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduceFromClasspath.setAccessible(true);// 因为是私有的，所以这里设置为true
        System.out.println("\t当前的应用类型为：" + deduceFromClasspath.invoke(null));

        System.out.println("3. 演示 ApplicationContext 初始化器");
        spring.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                if (applicationContext instanceof GenericApplicationContext gac) {
                    // 手动通过初始化器加入Bean，在后续遍历的Bean集合中也会找到对应的结果
                    gac.registerBean("bean3", Bean3.class);
                }
            }
        });

        System.out.println("4. 演示监听器与事件");
        spring.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("\t事件为：" + event.getClass());
            }
        });

        System.out.println("5. 演示主类推断");
        Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        deduceMainApplicationClass.setAccessible(true);
        System.out.println("\t 主类是：" + deduceMainApplicationClass.invoke(spring));

        // 打印当前环境的 Bean 信息
        ConfigurableApplicationContext context = spring.run(args);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("bean name: " + name);
        }

        context.close();*/

        /*
        // 添加 app 监听器
        SpringApplication app = new SpringApplication(PaymentApplication.class);
        app.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println(event.getClass());
            }
        });

        // 获取事件发送器实现类名
        List<String> names = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, PaymentApplication.class.getClassLoader());
        for (String name : names) {
            System.out.println(name);
            Class<?> clazz = Class.forName(name);
            // 拿到 EventPublishingRunListener 对象的构造器
            Constructor<?> constructor = clazz.getConstructor(SpringApplication.class, String[].class);
            SpringApplicationRunListener publisher = (SpringApplicationRunListener) constructor.newInstance(app, args);
            // 发布事件
            DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();
            publisher.starting(bootstrapContext); // spring boot 开始启动
            publisher.environmentPrepared(bootstrapContext, new StandardEnvironment()); // 环境信息准备完毕
            GenericApplicationContext context = new GenericApplicationContext();
            publisher.contextPrepared(context); // 在 spring 容器创建，并调用初始化器之后，发送此事件
            publisher.contextLoaded(context); // 所有 bean definition 加载完毕
            context.refresh(); // spring 容器初始化好了
            publisher.started(context, Duration.ofNanos(20l)); // spring 容器初始化完成(refresh 方法调用完毕)
            publisher.ready(context, Duration.ofNanos(25l)); // spring boot 启动完毕

            publisher.failed(context, new Exception("出错了~"));// spring boot 启动出错

        }
         */

        SpringApplication app = new SpringApplication();
        // 自定义一个初始化器，看他什么时候被触发
        app.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                System.out.println("执行初始化器增强...");
            }
        });

        System.out.println(">>>>>>>>>>>>>>>>>>> 2.封装启动 args");
        DefaultApplicationArguments arguments = new DefaultApplicationArguments(args);

        System.out.println(">>>>>>>>>>>>>>>>>>> 8.创建容器");
        GenericApplicationContext context = createApplicationContext(WebApplicationType.SERVLET);

        System.out.println(">>>>>>>>>>>>>>>>>>> 9.准备容器");
        for (ApplicationContextInitializer initializer : app.getInitializers()) {
            initializer.initialize(context);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>> 10.加载 bean 定义");
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(context.getDefaultListableBeanFactory());
        reader.register(Config.class);

        System.out.println(">>>>>>>>>>>>>>>>>>> 11.refresh 容器");
        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("name:" + name + " 来源：" + context.getBeanFactory().getBeanDefinition(name).getResourceDescription());
        }

        System.out.println(">>>>>>>>>>>>>>>>>>> 12.执行 runner");
        // runner 方法执行有两种形式
        for (CommandLineRunner runner : context.getBeansOfType(CommandLineRunner.class).values()) {
            runner.run(args);
        }

        for (ApplicationRunner runner : context.getBeansOfType(ApplicationRunner.class).values()) {
            // 第二步封装的参数放进去
            runner.run(arguments);
        }

    }

    private static GenericApplicationContext createApplicationContext(WebApplicationType type) {
        GenericApplicationContext context = null;
        switch (type) {
            case SERVLET -> context = new AnnotationConfigServletWebApplicationContext();
            case REACTIVE -> context = new AnnotationConfigReactiveWebApplicationContext();
            case NONE -> context = new AnnotationConfigApplicationContext();
        }
        return context;
    }

    static class Bean3 {

    }

    static class Bean5 {

    }

    @Configuration
    static class Config {
        @Bean
        public Bean5 bean5() {
            return new Bean5();
        }

        @Bean
        public CommandLineRunner commandLineRunner() {
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    System.out.println("commandLineRunner()...");
                }
            };
        }

        @Bean
        public ApplicationRunner applicationRunner() {
            return new ApplicationRunner() {
                @Override
                public void run(ApplicationArguments args) throws Exception {
                    System.out.println("applicationRunner()...");
                }
            };
        }

    }

}
