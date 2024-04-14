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

        Emp emp2 = context.getBean("emp2", Emp.class);
        emp2.work();

        Emp emp3 = context.getBean("emp3", Emp.class);
        emp3.work();

        Emp emp4 = context.getBean("emp4", Emp.class);
        emp4.work();

        Dept dept1 = context.getBean("dept5", Dept.class);
        dept1.info();

        System.out.println("---------");
        Dept dept2 = context.getBean("dept6", Dept.class);
        dept2.info();
    }
}
