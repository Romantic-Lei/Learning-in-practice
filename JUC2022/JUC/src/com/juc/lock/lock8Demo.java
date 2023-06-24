package com.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * 对多线程的理解，8锁案例说明
 * 1.标准访问ab两个线程，a线程后面休眠200毫秒 -> 执行结果：先打印邮件后打印短信
 * 2.在sendEmail 发送短信方法里面休眠500毫秒 -> 执行结果：先打印邮件后打印短信
 * 3.在Phone类中新增一个无锁的hello方法，将原来的发送短信线程缓存hello方法 -> 执行结果：先打印hello后打印邮件
 * 4.有两个phone对象，两个线程分别调用发短信和邮件 -> 执行结果：先打印短信后打印邮件
 * 5.将原来电话中的两个锁方法变成静态方法，只创建一个手机对象 -> 执行结果：先打印邮件后打印短信
 * 6.还是两个静态方法，创建两个手机对象 -> 执行结果：先打印邮件后打印短信
 * 7.发送邮件还是静态加锁方法，发送短信变成锁方法，只创建一个手机对象 -> 执行结果：先打印短信后打印邮件
 * 8.发送邮件还是静态加锁方法，发送短信变成锁方法，创建两个个手机对象 -> 执行结果：先打印短信后打印邮件
 */
public class lock8Demo {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }, "a").start();
        TimeUnit.MILLISECONDS.sleep(200);

        new Thread(() -> {
            phone2.sendSMS();
            // phone.sendSMS();
            // phone2.sendSMS();
            // phone.sendSMS();
            // phone2.sendSMS();
            // phone.hello();
        }, "b").start();
    }
}

class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----- sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("----- sendSMS");
    }

    public void hello() {
        System.out.println("----- hello");
    }
}
