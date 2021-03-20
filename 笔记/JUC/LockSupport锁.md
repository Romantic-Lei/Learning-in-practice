## LockSupport

网页API学习网址：https://www.apiref.com/java11-zh/java.base/module-summary.html

用于创建锁和其他同步类的基本线程阻塞原语。



LockSupport在哪看？

https://www.apiref.com/java11-zh/java.base/java/util/concurrent/locks/package-summary.html

![1616120530(1)](images\1616120530(1).jpg)`

是什么？（https://www.apiref.com/java11-zh/java.base/java/util/concurrent/locks/LockSupport.html）

![1616122249](images\1616122249.jpg)

**用于创建锁和其他同步类的基本线程阻塞原语。**

LockSupport中的park()和unpark()的作用分别是阻塞线程和解除阻塞线程



## 线程等待唤醒机制(wait/notify)

##### 传统的线程等待和唤醒的方法

![16161226211](images\16161226211.jpg)

##### 3种让线程等待和唤醒的方法

1. 方式1：使用Object中的wait()方法让线程等待，使用Object中的Notify()方法唤醒线程

2. 方式2：使用JUC包中Condition的await()方法让线程等待，使用signal()方法唤醒线程

3. 方式3：LockSupport类可以阻塞当前线程以及唤醒指定被阻塞的线程

   

####线程等待唤醒机制(wait/notify)

**Object类中的wait和notify方法实现线程等待和唤醒**

```java
static Object objectLock = new Object();
public static void synchronizedWaitNotify(){
    new Thread(() -> {
        synchronized (objectLock) {
            System.out.println(Thread.currentThread().getName() + "\t" + "come in");
            try {
                // wait需要和synchronized一起使用
                objectLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "被唤醒");
    }, "A").start();

    new Thread(() -> {
        synchronized (objectLock){
            System.out.println(Thread.currentThread().getName() + "\t" + "通知");
            objectLock.notify();
        }
    }, "B").start();
}
```

异常1：wait方法和notify方法，两个都去掉同步代码块

```java
异常情况：
Exception in thread "A" Exception in thread "B" java.lang.IllegalMonitorStateException
```

异常2：将notify放在wait方法前面

程序无法执行，无法唤醒

**小总结：** wait和notify方法必须要在同步块或者方法里面成对出现使用；先wait后notify才OK，颠倒顺序后无法解锁。

**Condition接口中的await和signal方法实现线程的等待和唤醒**

```java
static Lock lock = new ReentrantLock();
static Condition condition = lock.newCondition();
 
public static void synchronizedAwaitSignal(){
    new Thread(() -> {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
        try {
            try {
                // await需要和lock一起使用
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "-----被唤醒");
        } finally {
            lock.unlock();
        }
    }, "A").start();

    new Thread(() -> {
        lock.lock();
        try {
            condition.signal();
            System.out.println(Thread.currentThread().getName() + "\t" + "-----通知");
        } finally {
            lock.unlock();
        }
    }, "B").start();

}
```

异常1：await和signal方法，两个都去掉lock.lock()和 lock.unlock();

```java
异常情况
Exception in thread "A" Exception in thread "B" java.lang.IllegalMonitorStateException
```

异常2：将await休眠一秒，让signal方法先执行

 程序无法执行，无法唤醒



#### 传统的synchronized和Lock实现等待唤醒通知的约束

线程先要获得并持有锁，必须在锁块（Synchronized或lock）中；必须要等



### LockSupport类中的park等待和unpark唤醒

是什么？

通过park()和unpark()方法来实现阻塞和唤醒线程的操作

![1616221421](images\1616221421.jpg)

##### 主要方法：

**API：**

![1616221779](images\1616221779.jpg)

**阻塞：**

park()/park(Object blocker)

![1616222203](images\1616222203.jpg)

permit默认是0，所以一开始调用park()方法，当前线程就会阻塞，直到别的线程将当前线程的permit设置为1时，park方法会被唤醒，然后会将permit再次设置为0并返回。

阻塞当前线程/阻塞传入的具体线程

**唤醒：**

unpark(Thread thread)

![1616222756](images\1616222756.jpg)

调用unpark(thread)方法后，就会将thread线程的许可permit设置为1（注意多次调用unpark方法，不会累加，permit还是1），会自动唤醒thread线程，即之前阻塞中的LockSupport()方法会立即返回。











#### 面试题

**为什么可用先唤醒线程后阻塞线程**？

因为unpark获得一个凭证，之后在调用park方法，就可以名正言顺的凭证消费，故不会阻塞。

**为什么唤醒两次后阻塞两次，但最终结果还是会阻塞线程？**

因为凭证的数量最多是1，连续调用两次unpark和调用一次unpark的效果是一样的，他们都只会增加一个凭证；而调用两次park却需要消费两个凭证，凭证不够，不能放行。

