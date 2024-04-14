package at.luojia.spring6.iocxml.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBook {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-di.xml");
        Book book = context.getBean("book", Book.class);
        System.out.println(book);

        Book bookCon = context.getBean("bookCon", Book.class);
        System.out.println(bookCon);

        // 员工对象
        Emp emp = context.getBean("emp", Emp.class);
        emp.work();
    }
}
