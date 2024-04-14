package at.luojia.spring6.iocxml.beanlife;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUser {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-life.xml");
        User user = (User)context.getBean("user");
        System.out.println("6.bean 对象创建完成，可以使用了");
        System.out.println(user);
        context.close();// 触发销毁
    }
}
