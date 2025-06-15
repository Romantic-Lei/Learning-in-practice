package com.luojia.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class A02Application {

    public static void main(String[] args) {
        // testCalssPathXmlApplication();
        // testFileSystemXmlApplicationContext();
        // testAnnotationConfigApplicationContext();
        testAnnotationConfigServerWebApplication();
    }

    // 最为经典的容器，基于 classpath下 xml 格式的配置文件来创建
    private static void testCalssPathXmlApplication() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("b01.xml");

        for (String bean : context.getBeanDefinitionNames()) {
            // 看看有哪些bean
            // 在配置文件中获取到并解析注入了 bean1 和 bean2
            System.out.println(bean);
        }
    }

    // 基于磁盘路径下 xml 格式的配置文件来创建
    private static void testFileSystemXmlApplicationContext() {
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext("src\\main\\resources\\b01.xml");
        for (String bean : context.getBeanDefinitionNames()) {
            // 看看有哪些bean
            // 在配置文件中获取到并解析注入了 bean1 和 bean2
            System.out.println(bean);
        }
    }

    // 较为经典的容器，基于 Java 配置类来创建
    private static void testAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        for (String bean : context.getBeanDefinitionNames()) {
            // 看看有哪些bean
            // 这里还会打印其他配置进来的bean信息 也会有自定义的 bean1 和 bean2
            System.out.println(bean);
        }
        // 可以获取到bean1 的信息 com.luojia.demo.A02Application$Bean1@6d4d66d2
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 较为经典的容器，基于 Java 配置类来创建，用于 web 环境
    private static void testAnnotationConfigServerWebApplication() {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    @Configuration
    static class WebConfig {
        @Bean
        public ServletWebServerFactory webServerFactory() {
            return new TomcatServletWebServerFactory();
        }
        @Bean
        public DispatcherServlet dispatcherServlet() {
            return new DispatcherServlet();
        }
        @Bean
        public DispatcherServletRegistrationBean  dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }
        @Bean("/hello")
        public Controller controller() {
            return ((request, response) -> {
                response.getWriter().println("hello");
                return null;
            });
        }
    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1) {
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }

    static class Bean1 {
    }

    static class Bean2 {
        private Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }
    }
}
